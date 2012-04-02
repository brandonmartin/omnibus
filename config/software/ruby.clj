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

(let [env
      (cond
       (and (is-os? "darwin") (is-machine? "x86_64"))
       { "CFLAGS" "-arch x86_64 -m64 -L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
         "LDFLAGS" "-arch x86_64 -R/opt/opscode/embedded/lib -L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"}
       omnibus.cross/crosscompiling?
       { "CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
         "LDFLAGS" "-Wl,-rpath /opt/opscode/embedded/lib -L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"}
       (is-os? "linux")
       { "CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
         "LDFLAGS" "-Wl,-rpath /opt/opscode/embedded/lib -L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"}
       (is-os? "solaris2")
       { "CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
         "LDFLAGS" "-R/opt/opscode/embedded/lib -L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"}
       )
      ]
(let [args (cond
            omnibus.cross/crosscompiling?
             ["--prefix=/opt/opscode/embedded"
              (str "--host=" omnibus.cross/*omnibus-cross-host*)
              "--with-opt-dir=/opt/opscode/embedded"
              "--enable-shared"
              "--disable-install-doc"
              "DLDFLAGS=-Wl,--export-all-symbols"]
            true
             ["--prefix=/opt/opscode/embedded"
              "--with-opt-dir=/opt/opscode/embedded"
              "--enable-shared"
              "--disable-install-doc"])
     ]


  (software "ruby"
            :source "ruby-1.9.2-p180"
            :steps [{:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/ruby/win32.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/ruby/mkconfig.rb.patch")]}

                    {:env env
                     :command "/opt/opscode/embedded/bin/autoconf"
                     }
                    {:env env
                     :command "./configure"
                     :args args }
                    {:env env
                     :command (if (is-os? "freebsd") "gmake" "make")}
                    {:env env
                     :command (if (is-os? "freebsd") "gmake" "make")
                     :args ["install"]}
                    ])))
