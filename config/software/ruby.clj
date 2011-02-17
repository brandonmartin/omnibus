;
; Author:: Adam Jacob (<adam@opscode.com>)
; Copyright:: Copyright (c) 2010 Opscode, Inc.
; License:: Apache License, Version 2.0
;
; Licensed under the Apache License, Version 2.0 (the "License");
; you may not use this file except in compliance with the License.
; You may obtain a copy of the License at
; 
;     http://www.apache.org/licenses/LICENSE-2.0
; 
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.
;

(software "ruby" :source "ruby-1.9.2-p0"
                 :steps [["./configure" "--prefix=/opt/opscode/embedded" "--with-opt-dir=/opt/opscode/embedded" "--enable-shared" "--disable-install-doc"]
                         ["env RPATH=/opt/opscode/embedded/lib" "make"]
                         ["env RPATH=/opt/opscode/embedded/lib" "make" "install"]])
