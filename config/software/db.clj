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

(software "db" :source "db-5.0.26.NC"
               :build-subdir "build_unix"
               :steps [
                       {:command (if (is-os? "freebsd") "perl" "true")
                        :args [ "-pi" "-e" "s/(freebsd1|freebsd\\[\\[123\\]\\])\\*/$1\\.\\*/g" (str *omnibus-build-dir* "/db-5.0.26.NC/dist/aclocal/libtool.m4") ]}
                       {:command (if (is-os? "freebsd") "sh" "true")
                        :args [ "-c" "cd ../dist && PATH=/opt/opscode/embedded/bin:$PATH ./s_config" ]}
                       {:command "../dist/configure" :args ["--prefix=/opt/opscode/embedded"]}
                       {:command "make"}
                       {:command "make" :args ["install"]}])
