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
(let [args (cond
            omnibus.cross/crosscompiling?
             ["--prefix=/opt/opscode/embedded"
              (str "--host=" omnibus.cross/*omnibus-cross-host*)
              "--without-shared"
              "--enable-term-driver"
              "--enable-sp-funcs"
              "--without-debug"]
            true
             ["--prefix=/opt/opscode/embedded"
              "--with-shared"
              "--without-debug"])
     ]

(software "ncurses59" :source "ncurses-5.9"
          :steps [{
		               :env {"LD_RUN_PATH" "/opt/opscode/embedded/lib"}
                   :command "./configure"
                   :args args }
                  {:env {"LD_RUN_PATH" "/opt/opscode/embedded/lib"} :command "make"}
                  {:env {"LD_RUN_PATH" "/opt/opscode/embedded/lib"} :command "make" :args ["install"]}])

))
