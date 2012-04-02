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
              (str "--target=" omnibus.cross/*omnibus-cross-host*)
              "--disable-multilib"
              ;; these are only needed for freebsd due to missing
              ;; objective c compiler in the default system compiler
              ;; move to somewhere else probably
              "CC=gcc42" "CXX=g++42" ]
            true
             [ "--prefix=/nonexistent" ])
     ]

(software "mingw-w64-cross-binutils"
    :source "binutils-2.22"
    :build-subdir "build"
    :steps [
	    {
	     :command "../configure"
	     :args args
	     }
	    { :command "gmake" }
	    { :command "gmake" :args ["install"]}
	    ]))

