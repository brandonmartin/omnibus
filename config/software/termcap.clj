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
              (str "--host=" omnibus.cross/*omnibus-cross-host*) ]
            true
             ["--prefix=/opt/opscode/embedded"])
     ]

(software "termcap" :source "termcap-1.3.1"
          :steps (if omnibus.cross/crosscompiling?
                   [
                    {:command "./configure" :args args}
                    {:command "make"
                     :args [ (str "CC=" omnibus.cross/*omnibus-cross-host* "-gcc")]}
                    {:command (str omnibus.cross/*omnibus-cross-host* "-gcc")
                     :args [ "-shared" 
                             "-Wl,--out-implib,libtermcap.dll.a"
                             "-o" "libtermcap-0.dll"
                             "termcap.o" "tparam.o" "version.o" ]}
                    {:command "make"
                     :args [ "install"
                             "prefix=/opt/opscode/embedded"
                             "exec_prefix=/opt/opscode/embedded"
                             "libdir=/opt/opscode/embedded/lib"
                             "oldincludedir="
                             (str "RANLIB=" omnibus.cross/*omnibus-cross-host* "-ranlib")]}
                    {:command "sh"
                     :args [ "-c" "mv /opt/opscode/embedded/info/* /opt/opscode/embedded/share/info" ]}
                    {:command "install"
                     :args [ "-m" "0755" "libtermcap-0.dll"
                             "/opt/opscode/embedded/bin" ]}
                    {:command "install"
                     :args [ "-m" "0755" "libtermcap.dll.a"
                             "/opt/opscode/embedded/lib" ]}
                    {:command "install"
                     :args [ "-m" "0755" "libtermcap.a"
                             "/opt/opscode/embedded/lib" ]}
                    ]
                   [{:command "true"}])))

