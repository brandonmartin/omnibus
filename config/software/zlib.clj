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

(let [env (cond
           (and (is-os? "darwin") (is-machine? "x86_64"))
           {
            "LDFLAGS" "-R/opt/opscode/embedded/lib -L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
            "CFLAGS" "-I/opt/opscode/embedded/include -L/opt/opscode/embedded/lib"
            }
           (is-os? "linux")
           {
            "LDFLAGS" "-Wl,-rpath /opt/opscode/embedded/lib -L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
            "CFLAGS" "-I/opt/opscode/embedded/include -L/opt/opscode/embedded/lib"
            })]
;; PATH=/opt/mingw-w64/bin:$PATH make PREFIX=x86_64-w64-mingw32- -f win32/Makefile.gcc
  (software "zlib"
            :source "zlib-1.2.5"
            :steps (if omnibus.cross/crosscompiling? 
                     [{:command "make"
                       :args [ (str "PREFIX=" omnibus.cross/*omnibus-cross-host* "-")
                              "-f" "win32/Makefile.gcc" ]}
                      {:command "cp"
                       :args [ "-iv" "zlib1.dll" 
                               "/opt/opscode/embedded/bin" ]}
                      {:command "cp"
                       :args [ "-iv" "zconf.h" "zlib.h"
                               "/opt/opscode/embedded/include" ]}
                      {:command "cp"
                       :args [ "-iv" "libz.a"
                               "/opt/opscode/embedded/lib" ]}
                      {:command "cp"
                       :args [ "-iv" "libzdll.a"
                               "/opt/opscode/embedded/lib/libz.dll.a" ]}
                     ] 
                     [
                    {
                     :command "./configure"
                     :env env
                     :args [ "--prefix=/opt/opscode/omnibus" ]
                     }
                    { :command "make" }
                    { :command "make" :args ["install"]}
                    ])))




