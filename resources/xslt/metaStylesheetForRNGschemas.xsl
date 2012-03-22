<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:wxsl="http://www.w3.org/1999/XSL/Transform2"
   version="2.0" exclude-result-prefixes="#all" xmlns:xlink="http://www.w3.org/1999/xlink"
   xmlns:teix="http://www.tei-c.org/ns/Examples" xmlns:rng="http://relaxng.org/ns/structure/1.0"
   xmlns:a="http://relaxng.org/ns/compatibility/annotations/1.0">

   <!-- ###########
    The Abbot meta-stylesheet was written, and is maintained, by Brian L. Pytlik Zillig. 
    
    The meta-stylesheet takes a RelaxNG schema as an input file and *outputs* an XSLT 
    stylesheet that coverts arbitrary XML files into a format that conforms to the 
    RelaxNG input schema. 
    
    Copyright © 2007-20012 The Board of  Regents of the University of Nebraska.  
    
    All rights reserved. Please see LICENSE for details.
  ##########  -->

   <xsl:strip-space elements="*"/>

   <xsl:namespace-alias stylesheet-prefix="wxsl" result-prefix="xsl"/>

   <xsl:param name="date" select="current-date()"/>

   <xsl:variable name="apostrophe">'</xsl:variable>

   <xsl:variable name="attributeNameParam">{lower-case(name())}</xsl:variable>

   <xsl:variable name="leftOfVariable">{$</xsl:variable>

   <xsl:variable name="rightOfVariable">}</xsl:variable>

   <xsl:variable name="logging">off</xsl:variable>

   <xsl:variable name="logEntry">
      <xsl:choose>
         <xsl:when test="lower-case($logging) = 'on'">
            <xsl:element name="xsl:comment">
               <xsl:copy-of select="$nodePath" copy-namespaces="no"/>
               <xsl:element name="xsl:comment">
                  <wxsl:value-of select="$templateID"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of select="$desc"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of select="name()"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of select="$thisNodeAfterTransformation/*[name()!='emptyNode']/name()"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:copy-of
                     select="d:levenshteintest(string(name()),string($thisNodeAfterTransformation/*[name()!='emptyNode']/name()))"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of select="count(descendant::*)"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of
                     select="if (boolean($thisNodeAfterTransformation)=false) then 0 else count($thisNodeAfterTransformation//*[name()!='emptyNode'])-1"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of
                     select="number(if (boolean($thisNodeAfterTransformation)=false) then 0 else count($thisNodeAfterTransformation//*[name()!='emptyNode'])-1) - count(descendant::*)"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of select="distinct-values(descendant::*/name())"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of
                     select="distinct-values($thisNodeAfterTransformation/child::*[name()!='emptyNode']/descendant::*/name())"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of select="$listOfAttributes"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of
                     select="distinct-values($thisNodeAfterTransformation/child::*[name()!='emptyNode']/@*/name())"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of select="count(@*/name())"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of
                     select="count($thisNodeAfterTransformation/child::*[name()!='emptyNode']/@*)"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of
                     select="number(count(@*/name())) - number(count($thisNodeAfterTransformation/child::*[name()!='emptyNode']/@*))"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:variable name="allCurrentTextNodesInputFile">
                     <wxsl:for-each select="current()/text()">
                        <wxsl:value-of select="."/>
                     </wxsl:for-each>
                  </wxsl:variable>
                  <wxsl:value-of select="string-length($allCurrentTextNodesInputFile)"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:variable name="allCurrentTextNodesOutputFile">
                     <wxsl:for-each
                        select="$thisNodeAfterTransformation/child::*[name()!='emptyNode']/current()/text()">
                        <wxsl:value-of select="."/>
                     </wxsl:for-each>
                  </wxsl:variable>
                  <wxsl:value-of select="string-length($allCurrentTextNodesOutputFile)"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of
                     select="string-length($allCurrentTextNodesInputFile) - string-length($allCurrentTextNodesOutputFile)"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:choose>
                     <wxsl:when
                        test="$allCurrentTextNodesInputFile = $allCurrentTextNodesOutputFile"/>
                     <wxsl:otherwise>
                        <wxsl:copy-of
                           select="d:levenshteintest(string($allCurrentTextNodesInputFile),string($allCurrentTextNodesOutputFile))"
                        />
                     </wxsl:otherwise>
                  </wxsl:choose>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:value-of select="current-dateTime()"/>
                  <wxsl:text> : </wxsl:text>
                  <wxsl:for-each select="ancestor-or-self::*">
                     <wxsl:variable name="nodeName">
                        <wxsl:value-of select="name()"/>
                     </wxsl:variable>
                     <wxsl:value-of select="name()"/>

                     <wxsl:for-each select="@*">
                        <wxsl:text>{@</wxsl:text>
                        <wxsl:value-of select="name()"/>
                        <wxsl:text>=</wxsl:text>
                        <wxsl:value-of select="."/>
                        <wxsl:text>}</wxsl:text>
                     </wxsl:for-each>

                     <wxsl:if test="following-sibling::*[name()=$nodeName]">
                        <wxsl:text>[</wxsl:text>
                        <wxsl:value-of select="count(preceding-sibling::*[name()=$nodeName]) + 1"/>
                        <wxsl:text>]</wxsl:text>
                     </wxsl:if>
                     <wxsl:text>/</wxsl:text>
                  </wxsl:for-each>

                  <wxsl:text> : </wxsl:text>

               </xsl:element>
            </xsl:element>
         </xsl:when>
         <xsl:otherwise><!-- otherwise add no log entry --></xsl:otherwise>
      </xsl:choose>
   </xsl:variable>

   <xsl:variable name="nodePath">
      <wxsl:variable name="nodePath">
         <wxsl:for-each select="parent::*">
            <wxsl:value-of select="name()"/>
            <wxsl:for-each select="@*">
               <wxsl:text>[@</wxsl:text>
               <wxsl:value-of select="name()"/>
               <wxsl:text>=</wxsl:text>
               <wxsl:value-of select="."/>
               <wxsl:text>]</wxsl:text>
            </wxsl:for-each>
            <wxsl:text>/</wxsl:text>
            <wxsl:if test="child::text()">
               <wxsl:for-each select="child::text()">
                  <wxsl:text> </wxsl:text>
               </wxsl:for-each>
            </wxsl:if>
         </wxsl:for-each>
         <wxsl:value-of select="name()"/>
      </wxsl:variable>
   </xsl:variable>

   <xsl:variable name="attList">
      <attList>
         <xsl:for-each select="//rng:define[contains(@name,'.attributes')]">
            <list id="{@name}">
               <xsl:for-each select="child::rng:ref[contains(@name,'.attribute.')]">
                  <item>
                     <xsl:value-of select="substring-after(@name,'attribute.')"/>
                  </item>
               </xsl:for-each>
            </list>
         </xsl:for-each>
      </attList>
   </xsl:variable>

   <xsl:output method="xml" indent="yes" encoding="utf-8"/>

   <xsl:template match="rng:value"/>

   <xsl:template match="a:documentation"/>

   <xsl:template match="rng:param[@name='pattern']"/>

   <xsl:template match="*[not(parent::*)]">
      <wxsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
         xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:d="function">
         <xsl:namespace name="xs">http://www.w3.org/2001/XMLSchema</xsl:namespace>
         <wxsl:strip-space elements="*"/>
         <wxsl:variable name="gapCharacter">&#9679;</wxsl:variable>
         <wxsl:output method="xml" indent="yes" encoding="utf-8"/>
         <wxsl:param name="file"/>
         <wxsl:param name="date"/>

         <wxsl:function name="d:levenshteintest">
            <wxsl:param name="s" as="xs:string"/>
            <wxsl:param name="t" as="xs:string"/>
            <wxsl:value-of select="d:levenshtein($s,$t)"/>
         </wxsl:function>

         <wxsl:function name="d:levenshtein">
            <wxsl:param name="s" as="xs:string"/>
            <wxsl:param name="t" as="xs:string"/>
            <wxsl:variable name="n" select="string-length($s)"/>
            <wxsl:variable name="m" select="string-length($t)"/>
            <wxsl:variable name="ss" select="string-to-codepoints($s)"/>
            <wxsl:variable name="tt" select="string-to-codepoints($t)"/>
            <wxsl:copy-of
               select="if ($n=0) 
          then
          $m
          else
          if ($m=0)
          then
          $n
          else
          d:levenshtein2(
          $m,
          $n,
          (for $i in (0 to $n) return $i,
          for $j in (1 to $m) return 
          ($j, for $i in (1 to $n) return 
          (if($ss[$i]=$tt[$j]) then 0 else 1))),
          $n+3)
          "
            />
         </wxsl:function>

         <wxsl:function name="d:levenshtein2">
            <wxsl:param name="m" as="xs:integer"/>
            <wxsl:param name="n" as="xs:integer"/>
            <wxsl:param name="a" as="xs:integer+"/>
            <wxsl:param name="p" as="xs:integer"/>
            <wxsl:choose>
               <wxsl:when test="$p=2+($n+1)*($m+1)">
                  <wxsl:copy-of select="$a[last()]"/>
               </wxsl:when>
               <wxsl:otherwise>
                  <wxsl:copy-of
                     select="d:levenshtein2($m,$n,
              ($a[position()&lt;$p],  
              min((
              $a[position()=$p - ($n+1)]+1,
              $a[position()=$p - 1] + 1,
              $a[position()=$p - ($n+2)]+$a[position()=$p]
              )),
              $a[position()&gt;$p]),
              if($p mod ($n+1) = 0) then $p+2 else $p+1)
              "
                  />
               </wxsl:otherwise>
            </wxsl:choose>
         </wxsl:function>

         <xsl:comment>XSLT processor used to create this stylesheet: <xsl:value-of select="system-property('xsl:vendor')"/></xsl:comment>

         <!-- ########### begin implementation of the config file ########### -->
         <xsl:for-each
            select="document('http://abbot.unl.edu/abbot_config.xml')/*//transformation[@activate='yes']">

            <!--<xsl:comment>
               <xsl:text>Begin </xsl:text>
               <xsl:value-of select="child::desc"/>
            </xsl:comment>-->

            <xsl:element name="xsl:template">
               <xsl:attribute name="match">
                  <xsl:choose>
                     <xsl:when test="@type='xslt' and @activate='yes'">
                        <xsl:copy-of select="child::xsl:template/@match" copy-namespaces="no"/>
                     </xsl:when>
                     <xsl:when
                        test="lower-case(normalize-space(child::ident/@XPath)) != normalize-space(child::ident/@XPath)">
                        <xsl:value-of select="normalize-space(child::ident/@XPath)"/>
                        <xsl:text> | </xsl:text>
                        <xsl:value-of select="lower-case(normalize-space(child::ident/@XPath))"/>
                     </xsl:when>
                     <xsl:otherwise>
                        <xsl:value-of select="normalize-space(child::ident/@XPath)"/>
                     </xsl:otherwise>
                  </xsl:choose>
               </xsl:attribute>
               <xsl:attribute name="priority">
                  <xsl:value-of
                     select="child::element/child::priority/@choice | child::xsl:template/@priority"
                  />
               </xsl:attribute>

               <!-- begin writing the param that gives an ID# to the template for identification in the log -->
               <xsl:element name="xsl:param">
                  <xsl:attribute name="name">
                     <xsl:text>templateID</xsl:text>
                  </xsl:attribute>
                  <xsl:value-of select="generate-id()"/>
               </xsl:element>
               <!-- end writing the param that gives an ID# to the template for identification in the log -->

               <!-- begin writing the param that describes the template for use in the log -->
               <xsl:element name="xsl:param">
                  <xsl:attribute name="name">desc</xsl:attribute>
                  <xsl:value-of select="child::desc"/>
               </xsl:element>
               <!-- end writing the param that describes the template for use in the log -->

               <!-- begin writing the param that IDs the element's ATTRIBUTES for use in the log -->
               <xsl:element name="xsl:param">
                  <xsl:attribute name="name">listOfAttributes</xsl:attribute>
                  <wxsl:value-of select="distinct-values(@*/name())"/>
               </xsl:element>
               <!-- end writing the param that IDs the template for use in the log -->

               <!-- begin writing the meta-variable that writes the $elementName variable in the conversion stylesheet  -->
               <wxsl:variable name="elementName">
                  <wxsl:value-of select="lower-case(name())"/>
               </wxsl:variable>
               <!-- end writing the meta-variable that writes the $elementName variable in the conversion stylesheet  -->

               <!-- $$$$$ Begin transformation described in abbot_config.xml $$$$$ -->

               <!-- begin writing the variable that describes this node after transformation -->
               <xsl:element name="xsl:variable">
                  <xsl:attribute name="name">thisNodeAfterTransformation</xsl:attribute>

                  <emptyNode delete="no"/>
                  <!-- 'emptyNode' ensures that this variable is not typed as a string -->

                  <xsl:choose>

                     <xsl:when test="@type='xslt' and @activate='yes'">
                        <xsl:copy-of select="child::xsl:template/*" copy-namespaces="no"
                           exclude-result-prefixes="#all"/>
                     </xsl:when>

                     <xsl:when
                        test="child::element/@choice='change' and descendant::attribute/@choice='delete'">
                        <!-- for changing an element by DELETING its attribute(s) -->
                        <wxsl:element
                           name="{concat($leftOfVariable,'elementName',$rightOfVariable)}">
                           <wxsl:apply-templates/>
                        </wxsl:element>
                     </xsl:when>
                     <xsl:when
                        test="child::element/@choice='delete' 
              and descendant::attribute/@choice='change' 
              and descendant::content/@use[starts-with(.,'@')] 
              and descendant::content/@choice='text'">
                        <!-- for deleting an element and replacing it with the value of that element's attribute -->
                        <xsl:element name="xsl:value-of">
                           <xsl:attribute name="select">
                              <xsl:value-of select="descendant::content/@use"/>
                           </xsl:attribute>
                        </xsl:element>
                     </xsl:when>
                     <xsl:when
                        test="child::element/@choice='change' and descendant::attribute/@choice='add'">
                        <!-- for changing an element by ADDING an attribute or attributes -->
                        <wxsl:element
                           name="{concat($leftOfVariable,'elementName',$rightOfVariable)}">
                           <wxsl:attribute name="{descendant::attribute/@n}">
                              <xsl:choose>
                                 <xsl:when test="descendant::content/@choice='text'">
                                    <wxsl:value-of select="."/>
                                 </xsl:when>
                              </xsl:choose>
                           </wxsl:attribute>
                           <wxsl:apply-templates/>
                        </wxsl:element>
                     </xsl:when>
                  </xsl:choose>
               </xsl:element>

               <!-- @@@@ Begin custom log entry. @@@@ -->
               <xsl:copy-of select="$logEntry" copy-namespaces="no"/>
               <!-- @@@@ End custom log entry. @@@@ -->

               <wxsl:if
                  test="count($thisNodeAfterTransformation/child::*) &gt; 1 and ($thisNodeAfterTransformation/child::emptyNode)">
                  <wxsl:copy-of select="$thisNodeAfterTransformation/child::*[name()!='emptyNode']"
                  />
               </wxsl:if>

               <!-- $$$$$ End transformation described in abbot_config.xml $$$$$ -->

            </xsl:element>

            <!--<xsl:comment>
               <xsl:text>End </xsl:text>
               <xsl:value-of select="child::desc"/>
            </xsl:comment>-->

         </xsl:for-each>

         <!-- ########### end implementation of the config file ########### -->

         <!--dfgdfgdgdfgdfgdgdgdfg   -->
         <xsl:apply-templates/>

         <wxsl:template name="forLoop">
            <wxsl:param name="loopNumber"/>
            <wxsl:param name="counterNumber"/>
            <wxsl:if test="number($loopNumber) &lt;= number($counterNumber)">
               <!-- retain for debugging
                  [[[counterNumber:<wxsl:value-of select="$counterNumber"/>]]]
                  {{{loopNumber:<wxsl:value-of select="$loopNumber"/>}}}
               -->
               <wxsl:value-of select="$gapCharacter"/>
            </wxsl:if>
            <wxsl:if test="number($loopNumber) &lt;= number($counterNumber)">
               <wxsl:call-template name="forLoop">
                  <wxsl:with-param name="loopNumber">
                     <wxsl:value-of select="$loopNumber + 1"/>
                  </wxsl:with-param>
                  <wxsl:with-param name="counterNumber">
                     <wxsl:value-of select="$counterNumber"/>
                  </wxsl:with-param>
               </wxsl:call-template>
            </wxsl:if>
         </wxsl:template>

      </wxsl:stylesheet>
   </xsl:template>

   <!-- hide this: <define name="data.probability">
      <data type="double">
         <param name="minInclusive">0</param>
         <param name="maxInclusive">1</param>
      </data>
   </define> -->

   <xsl:template match="rng:define[not(child::rng:element)]"/>

   <xsl:template match="rng:define[child::rng:element]">

      <xsl:variable name="attributeClasses">
         <attributeClasses>
            <list>
               <xsl:for-each select="descendant::rng:ref[starts-with(@name,'att.')]">
                  <item>
                     <xsl:value-of select="@name"/>
                  </item>
               </xsl:for-each>
            </list>
         </attributeClasses>
      </xsl:variable>

      <xsl:variable name="namedAttributes">
         <list>
            <xsl:for-each select="descendant::rng:attribute">
               <item>
                  <xsl:value-of select="@name"/>
               </item>
            </xsl:for-each>
         </list>
      </xsl:variable>

      <xsl:variable name="attributeName">
         <xsl:value-of select="@name"/>
      </xsl:variable>

      <xsl:variable name="attributeNameLowercase">
         <xsl:value-of select="lower-case(@name)"/>
      </xsl:variable>

      <xsl:element name="xsl:template">
         <xsl:attribute name="match">
            <!-- begin writing of match value -->
            <xsl:choose>
               <xsl:when test="$attributeName = $attributeNameLowercase">
                  <xsl:value-of select="@name"/>
                  <xsl:text> | </xsl:text>
                  <xsl:value-of select="upper-case(@name)"/>
               </xsl:when>
               <xsl:otherwise>
                  <xsl:value-of
                     select="concat(@name,' | ',lower-case(@name),' | ',upper-case(@name))"/>
               </xsl:otherwise>
            </xsl:choose>
            <!-- end writing of match value -->
         </xsl:attribute>

         <!-- begin writing the param that describes the template for use in the log -->
         <xsl:element name="xsl:param">
            <xsl:attribute name="name">desc</xsl:attribute>
            <xsl:text>examine </xsl:text>
         </xsl:element>
         <!-- end writing the param that describes the template for use in the log -->

         <!-- begin writing the param that IDs the template for use in the log -->
         <xsl:element name="xsl:param">
            <xsl:attribute name="name">templateID</xsl:attribute>
            <xsl:value-of select="generate-id()"/>
         </xsl:element>
         <!-- end writing the param that IDs the template for use in the log -->

         <!-- begin writing the param that IDs the element's ATTRIBUTES for use in the log -->
         <xsl:element name="xsl:param">
            <xsl:attribute name="name">listOfAttributes</xsl:attribute>
            <wxsl:value-of select="distinct-values(@*/name())"/>
         </xsl:element>
         <!-- end writing the param that IDs the template for use in the log -->

         <!-- begin writing the variable that describes this node after transformation -->
         <xsl:element name="xsl:variable">
            <xsl:attribute name="name">thisNodeAfterTransformation</xsl:attribute>

            <xsl:element name="{$attributeName}" namespace="http://www.tei-c.org/ns/1.0">

               <xsl:namespace name="tei">http://www.tei-c.org/ns/1.0</xsl:namespace>

               <!-- begin writing attribute handler -->
               <wxsl:for-each select="./@*">
                  <wxsl:choose>
                     <xsl:for-each select="$attributeClasses//item">
                        <xsl:variable name="attClass">
                           <xsl:value-of select="."/>
                        </xsl:variable>
                        <xsl:for-each select="$attList//list[@id = $attClass]">
                           <xsl:for-each select="child::item[string-length(.) &gt; 0]">
                              <wxsl:when>
                                 <xsl:attribute name="test">
                                    <xsl:value-of
                                       select="concat('lower-case(name())=',$apostrophe,.,$apostrophe)"
                                    />
                                 </xsl:attribute>
                                 <wxsl:attribute name="{$attributeNameParam}">
                                    <wxsl:value-of select="."/>
                                 </wxsl:attribute>
                              </wxsl:when>
                           </xsl:for-each>
                        </xsl:for-each>
                     </xsl:for-each>
                     <xsl:for-each select="$namedAttributes//item">
                        <wxsl:when>
                           <xsl:attribute name="test">
                              <xsl:value-of
                                 select="concat('lower-case(name())=',$apostrophe,.,$apostrophe)"/>
                           </xsl:attribute>
                           <wxsl:attribute name="{$attributeNameParam}">
                              <wxsl:value-of select="."/>
                           </wxsl:attribute>
                        </wxsl:when>
                     </xsl:for-each>
                     <wxsl:otherwise/>
                  </wxsl:choose>
               </wxsl:for-each>
               <!-- end writing attribute handler -->

               <wxsl:apply-templates/>

            </xsl:element>

         </xsl:element>
         <!-- end writing the variable that describes this node after transformation -->

         <!-- ++++ Begin DEFAULT log entry. ++++ -->
         <xsl:copy-of select="$logEntry" copy-namespaces="no"/>
         <!-- ++++ End DEFAULT log entry. ++++ -->

         <wxsl:copy-of select="$thisNodeAfterTransformation"/>

      </xsl:element>
   </xsl:template>

</xsl:stylesheet>
