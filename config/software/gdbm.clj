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
            (and (is-os? "freebsd") (or (is-platform-version-like? "10") (is-platform-version-like? "9")))
             ["--prefix=/opt/opscode/embedded"
              "--with-pic"]
            true
             ["--prefix=/opt/opscode/embedded"])
     ]

(software "gdbm" :source "gdbm-1.8.3"
          :steps [
                  {:command (if (is-os? "freebsd") "perl" "true")
                   :args [ "-pi" "-e" "s/(freebsd1|freebsd\\[123\\])\\*/$1\\.\\*/g" (str *omnibus-build-dir* "/gdbm-1.8.3/aclocal.m4") ]}
                  {:command (if (is-os? "freebsd") "perl" "true")
                   :args [ "-pi" "-e" "s/(objformat \\|\\|)/$1 test -x \\/usr\\/bin\\/file \\&\\& \\/usr\\/bin\\/file \\/usr\\/bin\\/file \\| grep -v ELF \\>\\/dev\\/null \\|\\|/g" (str *omnibus-build-dir* "/gdbm-1.8.3/aclocal.m4") ]}

                  {:command "/opt/opscode/embedded/bin/autoconf"}
                  {:command "./configure" :args args }
                  {:command "make" :args ["BINOWN=root" "BINGRP=wheel"]}
                  {:command "make" :args ["install"]}]))
