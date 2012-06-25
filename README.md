
Abbot
=====

Version 0.4.7.

A sepulchral voice reverberating through the monastery, says . . .

*This is pre-release software.*  It may fail to compile.  It might have undocumented features.  It certainly has unimplemented features.  It definitely has bugs.  You have been warned.  See CHANGES for details on how things are going.

Description
-----------

Abbot is a tool for undertaking large-scale conversion of XML document collections in order to make them interoperable with one another.  In particular, Abbot can make one or more collections conform to a designated schema (including a schema used to define one of the collections).  In the simplest case, where the "target schema" is a proper subset of the schema(s) used to define the collections in question, Abbot operates more-or-less automatically.  More complicated cases require that you define specific transformations in a configuration file, but that configuration file uses a simple language unrelated to either XSLT or Schema.

[Note: The DSL used in the config file represents one of the major areas of volatility in this pre-release.  In fact, it won't actually exist for awhile.  In the meantime, you can still use XSLT.]

By default, Abbot converts documents into TEI Analytics -- a TEI subset designed for text analysis applications.

Abbot is designed to be furiously fast (it will automatically parallelize the conversion across n processor cores), and might be the right solution for jobs that don't explicitly involve interoperability, but merely seek to perform XSL transformations on massive document collections quickly.

Abbot is designed to run on UNIX-like systems, and is being developed on Linux.  Patches that make it play nice on other platforms will be warmly welcomed.

Quickstart
----------

(We'll assume that you have one or more collections that do not conform to TEI-A and you would like to make them interoperate with one another by making them conform to that schema.)

Everything you need to use Abbot (aside from an up-to-date JVM) is contained in the file abbot-X.X.X-standalone.jar.

1. Set the environment variable ABBOT\_HOME to this directory (the root directory of the Abbot distribution).

2. Put the files you want to convert into $ABBOT\_HOME/input.

3. type "java -jar abbot-X.X.X-standalone.jar"

4. Behold your converted files in $ABBOT\_HOME/output

[Note: Passing -h after the jar will show you some command-line switches that allow you to change the input and output directories.]

Slowstart
---------

In order to use Abbot effectively, it helps to understand a bit about how it works.

Abbot reads a schema (we'll call it the "target schema") and automatically generates a stylesheet designed to convert a set of XML documents into a form that (ideally) validates against that schema.  In its most basic form, it simply assumes that any element that appears in the document and which is also legal according to the target schema can be passed through unchanged.  So if the target schema describes a proper subset of the elements used by the documents, Abbot can perform the conversion without much effort on your part.

This works well for a surprising number of cases in which interoperability is the goal, but obviously won't work in more complicated cases.  When the mapping from some element in the documents to some element described in the schema is less obvious, you'll need to provide Abbot with a description of how that transformation should be undertaken.

The key files, then, are the target schema (which is kept in $ABBOT\_HOME/target) and the abbot\_config.xml file (which is kept in $ABBOT\_HOME/config).  The config file simply contains XSLT templates (under the <custom-transformations> element).  Each one of these will be added at runtime to the stylesheet that is automatically generated from the target schema.

[Note: Once again, replacing this config file with a simplified language is one of the major development goals of Abbot.  In the meantime, you have to provide your own XSLT templates.  Whether this is still worth it in your particular case really depends on how much customization is required, though it may be that having Abbot take care of the trivial cases for you helps you quite a bit.]

So if you want to start playing with different target schemas and custom mappings, those are the two files you want to replace and/or fiddle with.

Building from (and Tinkering with) the Source
---------------------------------------------

Abbot is built using a combination of XSLT and Clojure using the Leiningen build tool.  So assuming you have both Clojure (we're using version 1.4) and the current copy of Leiningen, you should be able to type:

lein deps
lein uberjar

to generate the current SNAPSHOT.

The abbot "runtime" is substantially written in Clojure, but that code is mainly concerned with compiling the "metastylesheet" (the stylesheet that generates the stylesheet that does the conversion -- or, as we call it, the "conversion stylesheet").  Once the conversion stylesheet is built (in the first few seconds of a typical run), Abbot will proceed to apply it to all the documents in the input directory.

So, if you're interested in tinkering with the XSLT part of things, you want to look in $ABBOT\_HOME/xslt (where the metastylesheet resides).  All other parts of the system -- the compilation of the stylesheets, parallelized conversion, etc. -- are in the src directory.
