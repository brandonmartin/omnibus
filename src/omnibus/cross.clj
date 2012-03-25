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

(ns omnibus.cross
  (:use [omnibus.log]
        [omnibus.core]
        [clojure.java.shell :only [sh]]
        [clojure.contrib.io :only [make-parents file-str]] )
  (:require [clojure.contrib.string :as str])
  (:gen-class))
  
(def *omnibus-cross-host* (get (System/getenv) "CROSS_HOST"))

(def crosscompiling? (not (= nil (get (System/getenv) "CROSS_HOST"))))

