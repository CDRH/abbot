(defproject edu.unl/abbot "0.8.1"
  :description "Abbot: A Conversion Tool for Text Interoperability"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.xml "0.0.7"]
                 [org.clojure/tools.logging "0.2.6"]
                 [clojure-saxon "0.9.3"]
                 [org.clojure/tools.cli "0.2.2"]
                 [log4j "1.2.17"]]
	:keep-non-project-classes true
  :jar-name "abbot.jar"
  :uberjar-name "abbot-0.8.0.jar"
	:main edu.unl.abbot.cli
	:aot [edu.unl.abbot.Abbot])
