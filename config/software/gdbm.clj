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

(software "gdbm" :source "gdbm-1.8.3"
          :steps [
                ;;  {:command (if omnibus.cross/crosscompiling? "patch" "true")
                ;;  :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                ;;                          omnibus.cross/*omnibus-cross-host*
                ;;                          "/gdbm/Makefile.in.patch")]}
                  {:command (if omnibus.cross/crosscompiling? "patch" "true")
                   :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                          omnibus.cross/*omnibus-cross-host*
                                          "/gdbm/dbminit.c.patch")]}
                  {:command (if omnibus.cross/crosscompiling? "patch" "true")
                   :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                          omnibus.cross/*omnibus-cross-host*
                                          "/gdbm/dbmopen.c.patch")]}
                  {:command (if omnibus.cross/crosscompiling? "patch" "true")
                   :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                          omnibus.cross/*omnibus-cross-host*
                                          "/gdbm/gdbmopen.c.patch")]}
                  {:command (if omnibus.cross/crosscompiling? "patch" "true")
                   :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                          omnibus.cross/*omnibus-cross-host*
                                          "/gdbm/gdbmreorg.c.patch")]}
                  {:command (if omnibus.cross/crosscompiling? "patch" "true")
                   :args ["-p0" "-i" (str omnibus.core/*omnibus-patch-dir* "/"
                                          omnibus.cross/*omnibus-cross-host*
                                          "/gdbm/systems.h.patch")]}
                  {:command "/opt/opscode/embedded/bin/autoconf"}
                  {:command "./configure" :args args}
                  {:command "make" :args ["BINOWN=root" "BINGRP=wheel"]}
                  {:command "make" :args ["install"]}]))
