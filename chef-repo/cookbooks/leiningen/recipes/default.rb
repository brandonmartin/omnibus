#
# Cookbook Name:: leiningen
# Recipe:: default
#
# Copyright 2011, Opscode, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

include_recipe "java"

remote_file "/usr/local/bin/lein" do
  source 'https://github.com/technomancy/leiningen/raw/stable/bin/lein'
  mode "0755"
  checksum "b8928e527f545ba8bd70744a8d42c4d89aad7b69"
end

