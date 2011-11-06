(defproject edu.unl/abbot "0.1.0-SNAPSHOT"
  :description "Abbot: A Conversion Tool for Text Interoperability"
  :license {:name "GNU Affero General Public License, version 3"
            :url "http://www.gnu.org/licenses/agpl-3.0.html"}
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/tools.cli "0.1.0"]
                 [clojure-saxon "0.9.2"]]
  :main edu.unl.abbot.core)
