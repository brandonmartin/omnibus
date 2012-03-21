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

(software "libiconv" :source "libiconv-1.13.1"
          :steps [
                  {:command (if (is-os? "freebsd") "perl" "true")
                   :args [ "-pi" "-e" "s/(freebsd1|freebsd\\[\\[123\\]\\]|freebsd\\[\\[12\\]\\])\\*/$1\\.\\*/g" (str *omnibus-build-dir* "/libiconv-1.13.1/m4/libtool.m4") ]}
                  {:command (if (is-os? "freebsd") "sh" "true")
                   :args [ "-c" "/opt/opscode/embedded/bin/aclocal -I m4 -I srcm4 --output=aclocal.m4 && touch aclocal.m4 && /opt/opscode/embedded/bin/automake --add-missing --gnits srclib/Makefile && /opt/opscode/embedded/bin/autoconf && /opt/opscode/embedded/bin/autoheader" ]}
                  {:command (if (is-os? "freebsd") "sh" "true")
                   :args [ "-c" "cd preload && /opt/opscode/embedded/bin/aclocal -I ../m4 -I ../srcm4 && /opt/opscode/embedded/bin/autoconf --include autoconf" ]}

                  {:command (if (is-os? "freebsd") "sh" "true")
                   :args [ "-c" "cd libcharset && /opt/opscode/embedded/bin/aclocal -I ../m4 -I ../srcm4 --output=autoconf/aclocal.m4 && /opt/opscode/embedded/bin/autoconf --include autoconf && /opt/opscode/embedded/bin/autoheader --include autoconf && touch config.h.in" ]}

                  {:env {"CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include" "LD_RUN_PATH" "/opt/opscode/embedded/lib"}
                   :command "./configure" :args args }
                  {:env {"CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include" "LD_RUN_PATH" "/opt/opscode/embedded/lib"}
                   :command "make"}
                  {:command "make"
                   :args ["install"]}]))
