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
             [(str "--prefix=" omnibus.cross/*omnibus-cross-path*)
              (str "--with-sysroot=" omnibus.cross/*omnibus-cross-path*)
              (str "--host=" omnibus.cross/*omnibus-cross-host*)
              "--disable-multilib" ]
            true
             [ "--prefix=/nonexistent" ])
     ]

(software "mingw-w64-cross-headers"
    :source "mingw-w64-v2.0.1"
    :steps [
      { :command "sh"
        :args [ "-c" "cd mingw-w64-headers && mkdir build" ]}
	    {
	     :command "sh"
	     :args [ "-c" (str "cd mingw-w64-headers/build && ../configure --host=" omnibus.cross/*omnibus-cross-host* " --prefix=" omnibus.cross/*omnibus-cross-path* ) ]
	     }
      { :command "sh"
        :args [ "-c" "cd mingw-w64-headers/build && gmake" ]}
      { :command "sh"
        :args [ "-c" "cd mingw-w64-headers/build && gmake install" ]}
      { :command "ln"
        :args [ "-s" (str omnibus.cross/*omnibus-cross-path* "/" omnibus.cross/*omnibus-cross-host*) (str omnibus.cross/*omnibus-cross-path* "/mingw") ]}
      { :command "ln"
        :args [ "-s" (str omnibus.cross/*omnibus-cross-path* "/" omnibus.cross/*omnibus-cross-host* "/lib") (str omnibus.cross/*omnibus-cross-path* "/" omnibus.cross/*omnibus-cross-host* "/lib64" ) ]}
;;	    { :command "make" }
;;	    { :command "make" :args ["install"]}
	    ]))

