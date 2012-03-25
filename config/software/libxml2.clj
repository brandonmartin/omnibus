;;
;; Author:: Adam Jacob (<adam@opscode.com>)
;; Author:: Christopher Brown (<cb@opscode.com>)
;; Copyright:: Copyright (c) 2010 Opscode, Inc.
;; License:: Apache License, Version 2.0
;;
;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;; 
;;     http://www.apache.org/licenses/LICENSE-2.0
;; 
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.
;;

(let [args (cond
            omnibus.cross/crosscompiling?
             ["--prefix=/opt/opscode/embedded"
              (str "--host=" omnibus.cross/*omnibus-cross-host*)
              "--with-threads=win32"
              "--with-zlib=/opt/opscode/embedded"
              "--with-readline=/opt/opscode/embedded"
              "--with-iconv=/opt/opscode/embedded"]
            true
             ["--prefix=/opt/opscode/embedded"
              "--with-zlib=/opt/opscode/embedded"
              "--with-readline=/opt/opscode/embedded"
              "--with-iconv=/opt/opscode/embedded"])
     ]
;; fix CFLAGS TODO
(software "libxml2" :source "libxml2-2.7.7"
          :steps [
                  {:command (if omnibus.cross/crosscompiling? "patch" "true")
                   :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                      omnibus.cross/*omnibus-cross-host*
                                      "/libxml2/xmlexports.h.patch")]}
                  {:env {"LDFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
                         "CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
                         "LD_RUN_PATH" "/opt/opscode/embedded/lib"}
                   :command "./configure"
                   :args args }
                  {:env {"LD_RUN_PATH" "/opt/opscode/embedded/lib"} :command "make"}
                  {:command (if omnibus.cross/crosscompiling? (str omnibus.cross/*omnibus-cross-host* "-gcc") "true")
;; TODO - I'm not sure why it doesnt produce a DLL so produce one manually for now..
;;        there has got to be a better way but the libxml2 build process eludes me for now
                   :args [ "-shared" 
                           "-Wl,--out-implib,.libs/libxml2.dll.a"
                           "-o" ".libs/libxml2.dll"
                           "DOCBparser.o" "HTMLparser.o" "HTMLtree.o" "SAX.o" "SAX2.o"
                           "c14n.o" "catalog.o" "chvalid.o" "debugXML.o" "dict.o"
                           "encoding.o" "entities.o" "error.o" "globals.o" "hash.o"
                           "legacy.o" "list.o" "nanoftp.o" "nanohttp.o" "parser.o"
                           "parserInternals.o" "pattern.o" "relaxng.o" "schematron.o"
                           "threads.o" "tree.o" "trio.o" "triostr.o" "uri.o" "valid.o"
                           "xinclude.o" "xlink.o" "xmlIO.o" "xmlmemory.o" "xmlmodule.o"
                           "xmlreader.o" "xmlregexp.o" "xmlsave.o" "xmlschemas.o"
                           "xmlschemastypes.o" "xmlstring.o" "xmlunicode.o" "xmlwriter.o"
                           "xpath.o" "xpointer.o"
                           "-L/opt/opscode/embedded/lib" "-lz" "-liconv" "-lws2_32" ]}
;; Monkey Monkey Monkey Monkey
                  {:command (if omnibus.cross/crosscompiling? "perl" "true")
                   :args [ "-pi" "-e" "s/(dlname=')/$1\\.\\.\\/bin\\/libxml2\\.dll/g"
                           (str *omnibus-build-dir* "/libxml2-2.7.7/.libs/libxml2.lai") ]}
                  {:command (if omnibus.cross/crosscompiling? "perl" "true")
                   :args [ "-pi" "-e" "s/(library_names=')/$1libxml2\\.dll\\.a/g"
                           (str *omnibus-build-dir* "/libxml2-2.7.7/.libs/libxml2.lai") ]}
                  {:command (if omnibus.cross/crosscompiling? "install" "true")
                   :args [ "-m" "0755" ".libs/libxml2.dll"
                           "/opt/opscode/embedded/bin" ]}
                  {:command (if omnibus.cross/crosscompiling? "install" "true")
                   :args [ "-m" "0755" ".libs/libxml2.dll.a"
                             "/opt/opscode/embedded/lib" ]}
                  {:env {"LD_RUN_PATH" "/opt/opscode/embedded/lib"} :command "make" :args ["install"]}]))

