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
              "--with-libxml-prefix=/opt/opscode/embedded"
              "--with-libxml-include-prefix=/opt/opscode/embedded/include"
              "--with-libxml-libs-prefix=/opt/opscode/embedded/lib"
              "--with-pic"]
            true
             ["--prefix=/opt/opscode/embedded"
              "--with-libxml-prefix=/opt/opscode/embedded"
              "--with-libxml-include-prefix=/opt/opscode/embedded/include"
              "--with-libxml-libs-prefix=/opt/opscode/embedded/lib"])
     ]


(software "libxslt" :source "libxslt-1.1.26"
          :steps [
                  {:command (if (is-os? "freebsd") "perl" "true")
                   :args [ "-pi" "-e" "s/(freebsd1|freebsd\\[123\\]|freebsd\\[\\[123\\]\\])\\*/$1\\.\\*/g" (str *omnibus-build-dir* "/libxslt-1.1.26/aclocal.m4") ]}
                  {:command (if (is-os? "freebsd") "perl" "true")
                   :args [ "-pi" "-e" "s/^(\\#if HAVE_LOCALTIME_R)/\\#if defined\\(HAVE_LOCALTIME_R\\) \\&\\& defined\\(__GLIBC__\\)/g"
                           (str *omnibus-build-dir* "/libxslt-1.1.26/libexslt/date.c") ]}
                  {:env {"LDFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
                         "CFLAGS" "-L/opt/opscode/embedded/lib -I/opt/opscode/embedded/include"
                         "LD_RUN_PATH" "/opt/opscode/embedded/lib"}
                   :command (cond (is-os? "darwin")
                                  "true"
                                  true
                                  "./autogen.sh")
                   :args args }
                  {:command (if (is-os? "solaris2") "perl" "true")
                   :args [ "-pi" "-e" "s/^(LIBXSLT_VERSION_SCRIPT = \\$.+)/\\# Woof/g"
                           (str *omnibus-build-dir* "/libxslt-1.1.26/libxslt/Makefile") ]}
                  {:command (if (is-os? "solaris2") "perl" "true")
                   :args [ "-pi" "-e" "s/^(#LIBXSLT_VERSION_SCRIPT.+)/LIBXSLT_VERSION_SCRIPT =/g" 
                           (str *omnibus-build-dir* "/libxslt-1.1.26/libxslt/Makefile") ]}
                  {:env {"LD_RUN_PATH" "/opt/opscode/embedded/lib"} :command "make" :args ["install"]}]))
