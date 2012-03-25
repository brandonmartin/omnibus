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
;; TODO we'd have to build gcrypt for this (and WILLDO)
              "--without-crypto"
              "--with-libxml-prefix=/opt/opscode/embedded"
              "--with-libxml-include-prefix=/opt/opscode/embedded/include"
              "--with-libxml-libs-prefix=/opt/opscode/embedded/lib"]
            true
             ["--prefix=/opt/opscode/embedded"
              "--with-libxml-prefix=/opt/opscode/embedded"
              "--with-libxml-include-prefix=/opt/opscode/embedded/include"
              "--with-libxml-libs-prefix=/opt/opscode/embedded/lib"])
     ]

(software "libxslt" :source "libxslt-1.1.26"
          :steps [{:env {"LDFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
                         "CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
                         "LD_RUN_PATH" "/opt/opscode/embedded/lib"}
                   :command (cond (is-os? "darwin")
                                  "true"
                                  true
                                  "./autogen.sh")
                   :args args }
;; probably due to outdated mingw autoheader (whats new) it comes up without snprintf
;; so patch it back in to config.status for now
                  {:command (if omnibus.cross/crosscompiling? "patch" "true")
                   :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                      omnibus.cross/*omnibus-cross-host*
                                      "/libxslt/config.status.patch")]}
                  {:command (if omnibus.cross/crosscompiling? "make" "true")
                   :args ["config.h"]}
;; mkdir is back to one argument in mingw-w64 (heh)
;; could probably be replaced with a macro
                  {:command (if omnibus.cross/crosscompiling? "patch" "true")
                   :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                      omnibus.cross/*omnibus-cross-host*
                                      "/libxslt/security.c.patch")]}
                  {:command (if (is-os? "solaris2") "perl" "true")
                   :args [ "-pi" "-e" "s/^(LIBXSLT_VERSION_SCRIPT = \\$.+)/\\# Woof/g"
                           (str *omnibus-build-dir* "/libxslt-1.1.26/libxslt/Makefile") ]}
                  {:command (if (is-os? "solaris2") "perl" "true")
                   :args [ "-pi" "-e" "s/^(#LIBXSLT_VERSION_SCRIPT.+)/LIBXSLT_VERSION_SCRIPT =/g" 
                           (str *omnibus-build-dir* "/libxslt-1.1.26/libxslt/Makefile") ]}
                  {:env {"LD_RUN_PATH" "/opt/opscode/embedded/lib"} :command "make" :args ["install"]}]))
