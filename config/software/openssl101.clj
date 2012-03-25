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

(software "openssl101" :source "openssl-1.0.1"
                    :steps [
                            {:command (if omnibus.cross/crosscompiling? "patch" "true")
                             :args ["-p0" "-i"
                                    (str omnibus.core/*omnibus-patch-dir* "/"
                                     omnibus.cross/*omnibus-cross-host*
                                     "/openssl/Configure.patch")]}
                            {:command (if omnibus.cross/crosscompiling? "patch" "true")
                             :args ["-d" "engines" "-p0" "-i"
                                    (str omnibus.core/*omnibus-patch-dir* "/"
                                     omnibus.cross/*omnibus-cross-host*
                                     "/openssl/engines_Makefile.patch")]}
                            {:command (if omnibus.cross/crosscompiling? "patch" "true")
                             :args ["-d" "engines/ccgost" "-p0" "-i"
                                    (str omnibus.core/*omnibus-patch-dir* "/"
                                     omnibus.cross/*omnibus-cross-host*
                                     "/openssl/engines_ccgost_Makefile.patch")]}
                            (cond
                             (and (is-os? "darwin") (is-machine? "x86_64"))
                              {
                               :command "./Configure"
                               :args ["darwin64-x86_64-cc"
                                      "--prefix=/opt/opscode/embedded"
                                      "--with-zlib-lib=/opt/opscode/embedded/lib"
                                      "--with-zlib-include=/opt/opscode/embedded/include"
                                      "zlib"
                                      "shared"]
                               }
                              (is-os? "solaris2")
                              {
                                :command "./Configure"
                                :args ["solaris-x86-gcc"
                                       "--prefix=/opt/opscode/embedded"
                                       "--with-zlib-lib=/opt/opscode/embedded/lib"
                                       "--with-zlib-include=/opt/opscode/embedded/include"
                                       "zlib"
                                       "shared"
                                       "-L/opt/opscode/embedded/lib"
                                       "-I/opt/opscode/embedded/include"
                                       "-R/opt/opscode/embedded/lib"]
                               }
                              omnibus.cross/crosscompiling?
                              {
                                :env {"LD_RUN_PATH" "/opt/opscode/embedded/lib"
                                      "CC" (str omnibus.cross/*omnibus-cross-host* "-gcc")
                                      "LD" (str omnibus.cross/*omnibus-cross-host* "-ld")
                                      "AR" (str omnibus.cross/*omnibus-cross-host* "-ar") 
                                      "WINDRES" (str omnibus.cross/*omnibus-cross-host* "-windres") 
                                      "RANLIB" (str omnibus.cross/*omnibus-cross-host* "-ranlib")}
                                :command "./Configure"
                                :args ["mingw64"
                                       "--prefix=/opt/opscode/embedded"
                                       "--with-zlib-lib=/opt/opscode/embedded/lib"
                                       "--with-zlib-include=/opt/opscode/embedded/include"
                                       "zlib"
                                       "shared"
                                 ;;      "no-asm"
                                 ;;      "no-dso"
                                       "no-hw"
                                       "-L/opt/opscode/embedded/lib"
                                       "-I/opt/opscode/embedded/include"]
                               }
                              true
                              {
                                :env {"LD_RUN_PATH" "/opt/opscode/embedded/lib"}
                                :command "./config"
                                :args ["--prefix=/opt/opscode/embedded"
                                       "--with-zlib-lib=/opt/opscode/embedded/lib"
                                       "--with-zlib-include=/opt/opscode/embedded/include"
                                       "zlib"
                                       "shared"
                                       "-L/opt/opscode/embedded/lib"
                                       "-I/opt/opscode/embedded/include"]
                               })
                            {
                             :env (if omnibus.cross/crosscompiling?
                                   {"LD_RUN_PATH" "/opt/opscode/embedded/lib"
                                    "CC" (str omnibus.cross/*omnibus-cross-host* "-gcc")
                                    "LD" (str omnibus.cross/*omnibus-cross-host* "-ld")
                                    "AR" (str omnibus.cross/*omnibus-cross-host* "-ar") 
                                    "WINDRES" (str omnibus.cross/*omnibus-cross-host* "-windres")
                                    "RANLIB" (str omnibus.cross/*omnibus-cross-host* "-ranlib")}
                                   {"LD_RUN_PATH" "/opt/opscode/embedded/lib"})
                             :command "make"
                             :args (if omnibus.cross/crosscompiling?
                                    [(str "CROSS_COMPILE=" omnibus.cross/*omnibus-cross-host* "-")]
                                    [""])
                             }
                            {
                             :command "make"
                             :args (if omnibus.cross/crosscompiling?
                                    ["install"
                                     (str "CROSS_COMPILE=" omnibus.cross/*omnibus-cross-host* "-")]
                                    ["install"])
                             }])

