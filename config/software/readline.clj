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

;; TODO: may need to move readline5.dll, history5.dll and assorted def files
;;       to the bin directory

(let [env
      (cond
       (and (is-os? "darwin") (is-machine? "x86_64"))
       { "CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
         "LDFLAGS" "-R/opt/opscode/embedded/lib -L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"}
       (is-os? "linux")
       { "CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
         "LDFLAGS" "-Wl,-rpath /opt/opscode/embedded/lib -L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"}
       (is-os? "solaris2")
       { "CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
         "LDFLAGS" "-Wl,-rpath /opt/opscode/embedded/lib -L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"}
       omnibus.cross/crosscompiling?
       { "CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
         "LDFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"}
       )
      ]
(let [args (cond
            omnibus.cross/crosscompiling?
             ["--prefix=/opt/opscode/embedded"
              "--enable-static"
              "--enable-shared"
              "--enable-multibyte"
              (str "--host=" omnibus.cross/*omnibus-cross-host*) ]
            true
             ["--prefix=/opt/opscode/embedded"])
     ]

  (software "readline" :source "readline-5.2"
            :steps [
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/Makefile.in.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/bind.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/chardefs.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/complete.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/config.h.in.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/config.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/configure.in.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/display.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/excallback.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/fileman.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/funmap.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/histexamp.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/histfile.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/history-dll-res.rc.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/history-dllversion.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/history.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/history.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/input.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/keymaps.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/kill.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/manexamp.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/nls.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/parens.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/posixdir.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/readline-dll-res.rc.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/readline-dllversion.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/readline.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/readline.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/readline.sln.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/readline.vcproj.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/readlinebuf.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/rl-fgets.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/rldefs.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/rlfe.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/rlmbutil.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/rlprivate.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/rlshell.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/rlstdc.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/rltest.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/rltty.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/rltty.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/shell.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/shlib_Makefile.in.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/shobj-conf.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/signals.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/terminal.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/tilde.c.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/tilde.h.patch")]}
                    {:command (if omnibus.cross/crosscompiling? "patch" "true")
                     :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                        omnibus.cross/*omnibus-cross-host*
                                        "/readline/util.c.patch")]}
                    
                    { :env env :command "./configure" :args args } 
                    {:command (if (is-os? "freebsd") "gmake" "make")}
                    {:command (if (is-os? "freebsd") "gmake" "make")
                     :args [ "install" ]}])))


