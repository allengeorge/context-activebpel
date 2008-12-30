<?xml version="1.0"?>

<!-- DocFrame XSL transformation for HTML 4 (CSS) output. Copyright (c) 2003 Scriptorium Publishing Services, Inc.

This XSL transformation file is part of the DocFrame authoring environment. Only licensed users may use or modify this file. Distribution is limited to other DocFrame licensees.

Revision history:
2/18/03     DocFrame 1.0 released
2/20/03     modified first-level navigation bar to correct broken link in
        Previous link on first content page
        added copyright statement to top of file
03/03/03    modified the transformation of graphics to handle graphics in
        the ImportedGraphic or AnchoredFrame element and to
        create ALT attributes in output
03/05/03    added meta tag with DocFrame identifier to each page template
        created variables for Next/Previous for localization or
        customization (button, etc.)
03/19/03    Modified code that creates footnotes
03/21/03    Added Audience element to list of second-level TOC links
03/24/03    Changed how cross-ref. formats with "step" are handled
03/25/03    Updated cross-ref. format conversion to correctly handle punctuation
        in heading and appendix formats
03/31/03    Changed cross-reference target code
03/31/03    Changed index entry target code
11/20/03        Wendy Beren:  COMMENTING OUT PAGE NAVIGATION IN FOOTER
01/20/04        mlf change: Mark Ford's initial changes for Eclipse TOC
01/30/04        DP change: Danny Power's changes for Eclipse TOC - Stage 1
02/18/04        DP change: Danny Power's changes for Eclipse TOC - Stage 2
02/24/04        DP change: Danny Power's changes for Eclipse TOC - Stage 2 (revised)
11/04/04        Wendy Beren: Replaced docframe.css with AWFServer.css for stylesheet link
11/04/04        Wendy Beren: Created an html template in which all generated html content is added to a table cell in a two-column, one-row table. The left-hand table cell contains a navigation bar, and the right-hand cell contains the help content.
-->

<xsl:stylesheet version="1.1"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:saxon="http://icl.com/saxon"
  extension-element-prefixes="saxon">
<xsl:output method="html"/>
<xsl:preserve-space elements="Code UserInput"/>
<xsl:strip-space elements="IndexEntry"/>

<!--DP change - Add runtime param-->
<xsl:param name="tocFileName"/>
<xsl:param name="maxSectionLevel"/>
<xsl:param name="coreHelpDir"/>
<xsl:param name="coreTocFile"/>
<!-- DP 2/18/2004 This allows transform to cross-reference with Core Help -->
<xsl:variable name="CoreTocDoc" select="document($coreTocFile)"/>

<!--DECLARE GLOBAL VARIABLES -->

<xsl:variable name="booktitle">
    <xsl:value-of select="/UserGuide/TitlePage/BookTitle"/>
</xsl:variable>

<xsl:variable name="rootname">Help</xsl:variable>

<xsl:variable name="copyright">Copyright (c) 2004-2007 Active Endpoints, Inc. </xsl:variable>

<xsl:variable name="index_pos">
    <xsl:value-of select="count(/UserGuide/AboutAuthors) + count(/UserGuide/Introduction) + count(/UserGuide/Chapter) + count(/UserGuide/Appendix) + count(/UserGuide/Glossary) + count(/UserGuide/Index)"/>
</xsl:variable>

<xsl:variable name="next_label">Next</xsl:variable>
<xsl:variable name="prev_label">Previous</xsl:variable>
<xsl:variable name="toc_label">Contents</xsl:variable>
<xsl:variable name="ix_label">Index</xsl:variable>

<!-- CREATE INDEX.HTML WITH CONTENTS LINKS -->



<!-- PROCESS CHAPTER-LEVEL ELEMENTS, EXCEPT FOR INDEX -->

<xsl:template match="/UserGuide">
<xsl:for-each select = "AboutAuthors|Introduction|Chapter|Appendix|Glossary">

<!--DEFINE VARIABLES NEEDED FOR LINKS  -->

<xsl:variable name="pos1" select="position()"/>
<xsl:variable name="next1" select="position()+1"/>
<xsl:variable name="prev1" select="position()-1"/>
<xsl:variable name="last1" select="last()"/>
<xsl:variable name="filename1"><xsl:value-of select="$rootname"/><xsl:value-of select="position()"/></xsl:variable>

<xsl:variable name="prevlinkcount1" select="count(preceding-sibling::*[1]/Section) + count(preceding-sibling::*[1]/Audience) + count(preceding-sibling::*[1]/Synopsis) + count(preceding-sibling::*[1]/SystemReq) + count(preceding-sibling::*[1]/Installation) + count(preceding-sibling::*[1]/Configuration) + count(preceding-sibling::*[1]/DocConventions)"/>

<xsl:variable name="prevlinkcountsub1" select="count(preceding-sibling::*[1]/child::*[last()]/Section)"/>

<xsl:variable name="prevlink1">
    <xsl:choose>
        <xsl:when test="$prev1 = '0'">index.html</xsl:when>
        <!-- DP change : this respects the level of transform -->
        <xsl:when test="$maxSectionLevel&gt;=3 and $prevlinkcountsub1 != 0"><xsl:value-of select="$rootname"/><xsl:value-of select="$prev1"/><xsl:text>-</xsl:text><xsl:value-of select="$prevlinkcount1"/><xsl:text>-</xsl:text><xsl:value-of select="$prevlinkcountsub1"/>.html</xsl:when>
        <xsl:when test="$maxSectionLevel&gt;=2 and $prevlinkcount1 != 0"><xsl:value-of select="$rootname"/><xsl:value-of select="$prev1"/><xsl:text>-</xsl:text><xsl:value-of select="$prevlinkcount1"/>.html</xsl:when>
        <xsl:otherwise><xsl:value-of select="$rootname"/><xsl:value-of select="$prev1"/>.html</xsl:otherwise>
    </xsl:choose>
</xsl:variable>

<xsl:variable name="nextlink1">
    <xsl:choose>
        <!-- DP change : this respects the level of transform -->
        <xsl:when test="$maxSectionLevel&gt;=2 and count(Section|Audience|Synopsis|SystemReq|Installation|Configuration|DocConventions) != 0"><xsl:value-of select="$filename1"/>-1.html</xsl:when>
        <xsl:otherwise><xsl:value-of select="$rootname"/><xsl:value-of select="$next1"/>.html</xsl:otherwise>
    </xsl:choose>
</xsl:variable>


<!-- CREATE DOCUMENT, CREATE ACTIVEWEBFLOW SERVER HEADER, DEFINE FILENAME BY DOCUMENT POSITION, LINK IN AWFServer.css-->

<xsl:document href="{$outputDir}/{$filename1}.html">
<html>
    <head>
    <meta name="generator" description="DocFrame 1.1 from Scriptorium Publishing"/>
    <link rel="stylesheet" type="text/css" href="AWFServer.css" />
    </head>
    <body>
     <a href="http://www.active-endpoints.com/"><img src="logo.gif" height="40" width="205" border="0" alt="ActiveBPEL Engine"> </img></a>
                        
        <table  style="border-collapse: collapse; border: none;" width="23%">
                
               <tr>
                    <td class="servernav-left"> <b>Resources</b>
                   </td>
                   
               </tr>
     </table>
     
    <xsl:call-template name="header"/>
        
<!-- Navigation bar Sect X.html-->       
    
<!-- COMMENTING OUT FOR ACTIVEBPEL SERVER 
    <p class="NavBarTop">
    <xsl:choose>
        <xsl:when test="$prev1 != '0'">
            <a href="{$prevlink1}"><xsl:value-of select="$prev_label"/></a>
        </xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="$prev_label"/>
        </xsl:otherwise>
    </xsl:choose>
    <xsl:text> | </xsl:text>
    <a href="{$nextlink1}"><xsl:value-of select="$next_label"/></a>
    <xsl:text> | </xsl:text>
    
    -->
    <!--<a href="index.html"><xsl:value-of select="$toc_label"/></a> | -->
<!--    <a href="{$rootname}{$index_pos}.html"><xsl:value-of select="$ix_label"/></a>
    </p>
    
    
    -->
<!--END navigation bar -->

<!-- APPLY TEMPLATES FOR NON-SECTION ELEMENTS. Section elements are processed as separate files. Only Para, Title, and the like should be included here.-->



<!-- TESTING DIV FORMAT FOR ACTIVEWEBFLOW SERVER NAV BAR -->


<div id="left" style="top: 130px;">

<table cellpadding="0" cellspacing="0">
    <tr>

        <!-- Table of Contents -->
        <td class="servernav-leftTOC">
            <table width="175px;" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="navTableHead"> 
                         Administration Console
                    </td>
                </tr>
                <tr>
                    <td class="content">
                        <a href="Help1.html">Console Overview</a><br/>
                        <a href="Help1-1.html">Home</a><br/>
                        <a href="Help1-2.html">Engine</a><br/>
                        <a href="Help1-3.html">Deployment Status</a><br/>
                        <a href="Help1-4.html">Process Status</a><br/>
                        <a href="Help1-5.html">Process ID and Process Details</a><br/>
                    </td>
                </tr>
            </table>
            <br/>
          
          <!-- NOT USING TABLE FOR RELEASE NOTES FOR ACTIVEBPEL
          <table width="175px;">
             <tr>
                <td class="navTableHead" >
                  Release Notes
              </td>
              </tr>
              <tr>
               <td class="content" >
                     <a href="doc/ReleaseNotes.htm">Release Notes</a><br/>
                     
                                 
                 </td>
            </tr>
            </table>
             <br/>
       -->
            <table width="175px;">
         <tr>
          <td class="navTableHead">
            Documentation Set
          </td>
         </tr>
         <tr>
            <td class="content" >
                
                <a href="doc/AdministrationConsole.pdf">Administration Console Help (.pdf)</a><br/>
                <br/>
                
              
           </td>
        </tr>
            </table>
            <br/>
            <table width="175px;">
            <tr>
             <td class="navTableHead">
             Support
             </td>
            </tr>
             <tr>
               <td class="content">
                  <a href="http://forums.active-endpoints.com">Active Endpoints Support</a><br/>
                                    
               </td>
            </tr>
            </table>
            <br/>
        </td>
    </tr>
</table>
        
</div>
        <!-- Body -->
        <div id="main">
         

            
        <xsl:apply-templates select="Title|Para|Figure|ContentsList|List|Table|Code|Note|Caution|Warning|GlossEntry|AuthorBio"/>

    <xsl:call-template name="footer">
    <xsl:with-param name="plink" select="$prevlink1"/>
    <xsl:with-param name="nlink" select="$nextlink1"/>
    </xsl:call-template>

        </div>

    

    </body>
    </html>

</xsl:document>

<!--Process first-level major Sections, including special Introduction sections -->
<xsl:for-each select= "Audience | Configuration | DocConventions | Installation | SystemReq | Synopsis | Section">

<!-- DEFINE VARIABLES -->

<xsl:variable name="pos2"><xsl:value-of select="position()"/></xsl:variable>
<xsl:variable name="next2" select = "position()+1"/>
<xsl:variable name="prev2" select="position()-1"/>
<xsl:variable name="last2" select="last()"/>
<xsl:variable name="filename2"><xsl:value-of select="$filename1"/>-<xsl:value-of select="$pos2"/></xsl:variable>
<xsl:variable name="prevlinkcount2" select="count(preceding-sibling::*[1]/Section)"/>

<xsl:variable name="prevlink2">
<xsl:choose>
<!--If this is not the first subsection, check for children of previous -->
    <xsl:when test="position() != 1">
        <xsl:choose>
            <!-- DP change : this respects the level of transform -->
            <xsl:when test = "$maxSectionLevel&gt;=3 and $prevlinkcount2 != 0"><xsl:value-of select="$filename1"/>-<xsl:value-of select="$prev2"/>-<xsl:value-of select="$prevlinkcount2"/>.html</xsl:when>
            <xsl:otherwise><xsl:value-of select="$filename1"/>-<xsl:value-of select="$prev2"/>.html</xsl:otherwise>
        </xsl:choose>
    </xsl:when>
    <!-- If this is the first subsection, then use the parent section -->
    <xsl:otherwise><xsl:value-of select="$filename1"/>.html</xsl:otherwise>
    </xsl:choose>
</xsl:variable>

<xsl:variable name="nextlink2">
<xsl:choose>
    <!-- DP change : this respects the level of transform -->
    <xsl:when test="$maxSectionLevel&gt;=3 and count(Section) != 0"><xsl:value-of select = "$filename2"/>-1.html</xsl:when>
    <xsl:when test="$maxSectionLevel&gt;=2 and position() != last()"><xsl:value-of select="$filename1"/>-<xsl:value-of select="$next2"/>.html</xsl:when>
    <xsl:otherwise><xsl:value-of select="$rootname"/><xsl:value-of select="$next1"/>.html</xsl:otherwise>
    </xsl:choose>
</xsl:variable>

<!-- DP change : this respects the level of transform -->
<xsl:if test="$maxSectionLevel&gt;=2">

<xsl:document href="{$outputDir}/{$filename2}.html">
<html>
    <head>
    <meta name="generator" description="DocFrame 1.1 from Scriptorium Publishing"/>
    <link rel="stylesheet" type="text/css" href="AWFServer.css" />
    </head>
    <body>
         <a href="http://www.active-endpoints.com/"><img src="logo.gif" height="40" width="205" border="0" alt="ActiveBPEL"> </img></a>
                        
        <table  style="border-collapse: collapse; border: none;" width="23%">
                
               <tr>
                    <td class="servernav-left"> <b>Resources</b>
                   </td>
                   
               </tr>
         </table>
         
    <xsl:call-template name="header"/>
    


<!-- NAVIGATION BAR FOR SECTION X-X -->

<!-- COMMENTING OUT FOR ACTIVEBPEL

    <p class="NavBarTop">
        <a href="{$prevlink2}"><xsl:value-of select="$prev_label"/></a>
        <xsl:text> | </xsl:text>
        <a href="{$nextlink2}"><xsl:value-of select="$next_label"/></a>
        <xsl:text> | </xsl:text>
        
    --> 
        <!--<a href="index.html"><xsl:value-of select="$toc_label"/></a> | -->
    <!--    <a href="{$rootname}{$index_pos}.html"><xsl:value-of select="$ix_label"/></a>
    </p>
-->
<!-- END NAV BAR -->

<!-- APPLY TEMPLATES FOR NON-SECTION ELEMENTS. Section elements are processed as separate files. Only Para, Title, and the like should be included here.-->

    
    
    <!-- TESTING DIV FORMAT FOR ACTIVEBPEL SERVER NAV BAR -->
    
    
<div id="left" style="top: 130px;">

<table cellpadding="0" cellspacing="0">
    <tr>

        <!-- Table of Contents -->
        <td class="servernav-leftTOC">
            <table width="175px;" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="navTableHead"> 
                         Administration Console
                    </td>
                </tr>
                <tr>
                    <td class="content">
                        <a href="Help1.html">Console Overview</a><br/>
                        <a href="Help1-1.html">Home</a><br/>
                        <a href="Help1-2.html">Engine</a><br/>
                        <a href="Help1-3.html">Deployment Status</a><br/>
                        <a href="Help1-4.html">Process Status</a><br/>
                        <a href="Help1-5.html">Process ID and Process Details</a><br/>
                    </td>
                </tr>
            </table>
            <br/>
            <!-- NOT USING TABLE FOR RELEASE NOTES FOR ACTIVEBPEL
	              <table width="175px;">
	                 <tr>
	                    <td class="navTableHead" >
	                      Release Notes
	                  </td>
	                  </tr>
	                  <tr>
	                   <td class="content" >
	                         <a href="doc/ReleaseNotes.htm">Release Notes</a><br/>
	                         
	                                     
	                     </td>
	                </tr>
	                </table>
	                 <br/>
       -->
            <table width="175px;">
         <tr>
          <td class="navTableHead">
            Documentation Set
          </td>
         </tr>
         <tr>
            <td class="content" >
                
                
                <a href="doc/AdministrationConsole.pdf">Administration Console Help (.pdf)</a><br/>
                                
              
           </td>
        </tr>
            </table>
            <br/>
            <table width="175px;">
            <tr>
             <td class="navTableHead">
             Support
             </td>
            </tr>
             <tr>
               <td class="content">
                  <a href="http://forums.active-endpoints.com">Active Endpoints Support</a><br/>
                                    
               </td>
            </tr>
            </table>
            <br/>
        </td>
    </tr>
</table>
        
</div>          
    
            <!-- Body -->
            
<div id="main">          
    
                
            <xsl:apply-templates select="Title|Para|Figure|ContentsList|List|Table|Code|Note|Caution|Warning|GlossEntry|AuthorBio"/>
    
        <xsl:call-template name="footer">
        <xsl:with-param name="plink" select="$prevlink1"/>
        <xsl:with-param name="nlink" select="$nextlink1"/>
        </xsl:call-template>
    
           
</div>  
    </body>
    </html>

</xsl:document>

</xsl:if>

<!--Process second-level Sections -->

<xsl:for-each select="Section">
<xsl:variable name="pos3"><xsl:value-of select="position()"/></xsl:variable>
<xsl:variable name="next3" select = "position()+1"/>
<xsl:variable name="prev3" select="position()-1"/>
<xsl:variable name="filename3"><xsl:value-of select="$filename2"/>-<xsl:value-of select="$pos3"/></xsl:variable>
<xsl:variable name="prevlinkcount3" select="count(preceding-sibling::*[1]/Section)"/>
<xsl:variable name="prevlink3">
    <xsl:choose>
    <!--If this is not the first subsection, use the previous subsection -->
    <!-- DP change : this respects the level of transform -->
        <xsl:when test="$maxSectionLevel&gt;=3 and position() != 1"><xsl:value-of select="$filename2"/>-<xsl:value-of select="$prev3"/>.html</xsl:when>
        <xsl:otherwise><xsl:value-of select="$filename2"/>.html</xsl:otherwise>
    </xsl:choose>
</xsl:variable>

<xsl:variable name="nextlink3">
<xsl:choose>
    <!-- DP change : this respects the level of transform -->
    <xsl:when test="$maxSectionLevel&gt;=3 and position() != last()"><xsl:value-of select="$filename2"/>-<xsl:value-of select="$next3"/>.html</xsl:when>
    <xsl:when test="$maxSectionLevel&gt;=2 and $pos2 = $last2"><xsl:value-of select="$rootname"/><xsl:value-of select="$next1"/>.html</xsl:when>
    <xsl:otherwise><xsl:value-of select="$filename1"/>-<xsl:value-of select="$next2"/>.html</xsl:otherwise>
    </xsl:choose>
</xsl:variable>

<!-- KICK OFF SECTION FILE -->

<!-- DP change : this respects the level of transform -->
<xsl:if test="$maxSectionLevel&gt;=3">

<xsl:document href="{$outputDir}/{$filename3}.html">
<html>
    <head>
    <meta name="generator" description="DocFrame 1.1 from Scriptorium Publishing"/>
    <link rel="stylesheet" type="text/css" href="docframe.css" />
    <title>
    <xsl:value-of select="Title"/>
    </title>
    </head>
    <body>
        <xsl:call-template name="header"/>
    
<!-- NAVIGATION BAR FOR SECTION X-X-X -->
    <p class="NavBarTop">
        <a href="{$prevlink3}"><xsl:value-of select="$prev_label"/></a>
        <xsl:text> | </xsl:text>
        <a href="{$nextlink3}"><xsl:value-of select="$next_label"/></a>
        <xsl:text> | </xsl:text>
        <!--<a href="index.html"><xsl:value-of select="$toc_label"/></a> | -->
        <a href="{$rootname}{$index_pos}.html"><xsl:value-of select="$ix_label"/></a>
    </p>
<!-- END NAV BAR -->

    <xsl:apply-templates/>

    <xsl:call-template name="footer">
        <xsl:with-param name="plink" select="$prevlink3"/>
        <xsl:with-param name="nlink" select="$nextlink3"/>
        </xsl:call-template>

    </body>
    </html>

</xsl:document>

</xsl:if>

</xsl:for-each>
</xsl:for-each>
</xsl:for-each>
<xsl:apply-templates select="Index"/>
</xsl:template>


<!-- GENERATE INDEX OUTPUT -->

<xsl:template match="Index">
<xsl:document href="{$outputDir}/index_temp.html" saxon:next-in-chain="index.xsl">

<!-- SAVE VARIABLES INTO ATTRIBUTES; WE'LL NEED THEM LATER -->

<Index>
<xsl:attribute name="prev1">
<xsl:value-of select = "$index_pos - 1"/>
</xsl:attribute>
<xsl:attribute name="rootname">
<xsl:value-of select="$rootname"/>
</xsl:attribute>
<xsl:attribute name="indexpos">
<xsl:value-of select="$index_pos"/>
</xsl:attribute>
<xsl:attribute name="booktitle">
<xsl:value-of select="$booktitle"/>
</xsl:attribute>
<xsl:attribute name="prevlinkcount1">
<xsl:value-of select="count(preceding-sibling::*[1]/Section)"/>
</xsl:attribute>
<xsl:attribute name="prevlinkcountsub1">
<xsl:value-of select="count(preceding-sibling::*[1]/Section[last()]/Section)"/>
</xsl:attribute>

    <xsl:apply-templates select="//IndexEntry/@text"/>

</Index>
</xsl:document>
</xsl:template>


<xsl:template match="//IndexEntry/@text">
    <xsl:call-template name="split">
      <xsl:with-param name="entry" select="."/>
    </xsl:call-template>
</xsl:template>


<!-- THE SPLIT TEMPLATE SPLITS UP MULTIPLE INDEX ENTRIES FROM A SINGLE MARKER INTO MULTIPLE ELEMENTS -->

<xsl:template name="split">
<xsl:param name="entry" select="''"/>
<!-- CALCULATE POSITION OF INDEX MARKER SO THAT WE CAN CREATE A LINK -->

<xsl:variable name="ancestors">
<xsl:value-of select="count(ancestor::*)"/>
</xsl:variable>

<xsl:variable name="link2indexcount">
<xsl:text>-</xsl:text><xsl:value-of select="count(ancestor::*[$ancestors - 2]/preceding-sibling::Section) + 
count(ancestor::*[$ancestors - 2]/preceding-sibling::Audience) + 
count(ancestor::*[$ancestors - 2]/preceding-sibling::Configuration) + count(ancestor::*[$ancestors - 2]/preceding-sibling::DocConventions) + count(ancestor::*[$ancestors - 2]/preceding-sibling::Installation) + count(ancestor::*[$ancestors - 2]/preceding-sibling::SystemReq) + count(ancestor::*[$ancestors - 2]/preceding-sibling::Synopsis) + 1"/>
</xsl:variable>


<xsl:variable name="link1">
<xsl:value-of select="count(ancestor::*[$ancestors - 1]/preceding-sibling::AboutAuthors) + count(ancestor::*[$ancestors - 1]/preceding-sibling::Introduction) + count(ancestor::*[$ancestors - 1]/preceding-sibling::Chapter) + count(ancestor::*[$ancestors - 1]/preceding-sibling::Appendix) + count(ancestor::*[$ancestors - 1]/preceding-sibling::Glossary) + count(ancestor::*[$ancestors - 1]/preceding-sibling::Index) + 1"/>
</xsl:variable>

<xsl:variable name="link2">

<xsl:if test="name(ancestor::*[$ancestors - 2])='Section'">
<xsl:value-of select="$link2indexcount"/>
</xsl:if>

<xsl:if test="name(ancestor::*[$ancestors - 2])='Audience'">
<xsl:value-of select="$link2indexcount"/>
</xsl:if>

<xsl:if test="name(ancestor::*[$ancestors - 2])='Synopsis'">
<xsl:value-of select="$link2indexcount"/>
</xsl:if>

<xsl:if test="name(ancestor::*[$ancestors - 2])='SystemReq'">
<xsl:value-of select="$link2indexcount"/>
</xsl:if>

<xsl:if test="name(ancestor::*[$ancestors - 2])='Installation'">
<xsl:value-of select="$link2indexcount"/>
</xsl:if>

<xsl:if test="name(ancestor::*[$ancestors - 2])='Configuration'">
<xsl:value-of select="$link2indexcount"/>
</xsl:if>

<xsl:if test="name(ancestor::*[$ancestors - 2])='DocConventions'">
<xsl:value-of select="$link2indexcount"/>
</xsl:if>

</xsl:variable>

<xsl:variable name="link3">
<xsl:if test="name(ancestor::*[$ancestors - 3])='Section'">
<xsl:text>-</xsl:text><xsl:value-of select="count(ancestor::*[$ancestors - 3]/preceding-sibling::Section) + 1 "/>
</xsl:if>


</xsl:variable>

<xsl:variable name="linkname">
    <xsl:value-of select="$rootname"/><xsl:value-of select="$link1"/><xsl:value-of select="$link2"/><xsl:value-of select="$link3"/>.html
</xsl:variable>

<!-- END LINKNAME CALCULATION -->

<xsl:choose>
    <!-- DP change : this respects the level of transform -->
    <xsl:when test="$link2!='' and $maxSectionLevel&lt;2"></xsl:when>
    <xsl:when test="$link3!='' and $maxSectionLevel&lt;3"></xsl:when>
    <xsl:otherwise>

    <xsl:choose>
    <!--delete $endrange markers-->
    <xsl:when test="starts-with($entry,'&lt;$endrange&gt;')"></xsl:when>
    <!--strip $startrange, but don't delete marker-->
    <xsl:when test="contains($entry,'&lt;$startrange&gt;')">
        <xsl:call-template name="split">
            <xsl:with-param name="entry" select="concat(substring-before($entry,'&lt;$startrange&gt;'),substring-after($entry,'&lt;$startrange&gt;'))"/>
            <xsl:with-param name="linkname" select="$linkname"/>
        </xsl:call-template>
    </xsl:when> 
    <xsl:when test="contains($entry,'&lt;i_italics&gt;')">
            <xsl:call-template name="split">
                <xsl:with-param name="entry" select="concat(substring-before($entry,'&lt;i_italics&gt;'),substring-after($entry,'&lt;i_italics&gt;'))"/>
                <xsl:with-param name="linkname" select="$linkname"/>
            </xsl:call-template>
    </xsl:when>
    <xsl:when test="contains($entry,'&lt;Default Para Font&gt;')">
                <xsl:call-template name="split">
                    <xsl:with-param name="entry" select="concat(substring-before($entry,'&lt;Default Para Font&gt;'),substring-after($entry,'&lt;Default Para Font&gt;'))"/>
                    <xsl:with-param name="linkname" select="$linkname"/>
                </xsl:call-template>
    </xsl:when> 
    <!--split up entries into individual index entries-->
    <xsl:when test="contains($entry,';')">
        <IXEntry>
        <xsl:call-template name="ixlevel">
            <xsl:with-param name="ixentry" select="substring-before($entry,';')"/>
            <xsl:with-param name="linkname" select="$linkname"/>
        </xsl:call-template>
        </IXEntry>
        <xsl:call-template name="split">
            <xsl:with-param name="entry" select="substring-after($entry,';')"/>
            <xsl:with-param name="linkname" select="$linkname"/>
        </xsl:call-template>
    </xsl:when>
<!-- process entries with no semicolons; thus no multiple entries-->
    <xsl:otherwise>
        <IXEntry>
            <xsl:choose>
                <xsl:when test="contains($entry,'&lt;$endrange&gt;')"></xsl:when>
                <xsl:when test="contains($entry,see) and contains($entry,'&lt;$nopage&gt;')">
                    <xsl:attribute name="see">yes</xsl:attribute>
                    <xsl:variable name="strip_nopage"><xsl:value-of select="concat(substring-before($entry,'&lt;$nopage&gt;'),substring-after($entry,'&lt;$nopage&gt;'))"/></xsl:variable>
                    <xsl:choose>
                        <xsl:when test="contains($strip_nopage,'[')">
                            <xsl:value-of select="concat(substring-before($strip_nopage,'['),substring-after($entry,']'))"/>
                        </xsl:when>
                        <xsl:otherwise><xsl:value-of select="$strip_nopage"/></xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="ixlevel">
                        <xsl:with-param name="ixentry" select="$entry"/>
                        <xsl:with-param name="linkname" select="$linkname"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </IXEntry>
    </xsl:otherwise>
    </xsl:choose>

    </xsl:otherwise>
</xsl:choose>

</xsl:template>

<!-- THE IXLEVEL TEMPLATE BREAKS UP MULTI-LEVEL INDEX ENTRIES INTO NESTED ELEMENTS. chocolate:bittersweet becomes <IXEntry>chocolate<IXEntry>bittersweet</IXEntry></IXEntry> -->

<xsl:template name="ixlevel">
    <xsl:param name="ixentry" select="''"/>
    <xsl:param name="linkname" select="''"/>

    <xsl:choose>
        <xsl:when test="contains($ixentry,'&lt;$endrange&gt;')"></xsl:when>
        <xsl:when test="contains($ixentry,':')">
            <xsl:variable name="temp" select="substring-before($ixentry,':')"/>
            <xsl:value-of select="normalize-space($temp)"/>
            <IXEntry>
            <xsl:call-template name="ixlevel">
                <xsl:with-param name="ixentry" select="substring-after($ixentry,':')"/>
                <xsl:with-param name="linkname" select="$linkname"/>
            </xsl:call-template>
            </IXEntry>
        </xsl:when>
        <xsl:otherwise>
            <xsl:attribute name="linkname"><xsl:value-of select="$linkname"/></xsl:attribute>
            <xsl:value-of select="normalize-space($ixentry)"/>
        </xsl:otherwise>
    </xsl:choose>

</xsl:template>

<!-- PROCESS CONTENTSLIST; INLINE TOC FOR EACH CHAPTER -->

<xsl:template match="ContentsList">
<xsl:apply-templates/><br/>

</xsl:template>
<xsl:template match="ContentsList/Title">
<p class="ContentsList">Topics:</p>
</xsl:template>

<xsl:template match="ContentItem">
<p class="ContentItem">
<xsl:apply-templates/>
</p>
</xsl:template>

<!-- PROCESS CROSS-REFERENCES -->

<xsl:template match="CrossReference">

<!-- CALCULATE TARGET FILENAME -->

<xsl:variable name="ancestors" select="count(id(@Idref)/ancestor-or-self::*)"/>

<xsl:variable name="link2refcount">
    <xsl:text>-</xsl:text><xsl:value-of select="count(id(@Idref)/ancestor-or-self::*[$ancestors - 2]/preceding-sibling::Section) +  count(id(@Idref)/ancestor-or-self::*[$ancestors - 2]/preceding-sibling::Audience) + count(id(@Idref)/ancestor-or-self::*[$ancestors - 2]/preceding-sibling::Configuration) + count(id(@Idref)/ancestor-or-self::*[$ancestors - 2]/preceding-sibling::DocConventions) + count(id(@Idref)/ancestor-or-self::*[$ancestors - 2]/preceding-sibling::Installation) + count(id(@Idref)/ancestor-or-self::*[$ancestors - 2]/preceding-sibling::SystemReq) + count(id(@Idref)/ancestor-or-self::*[$ancestors - 2]/preceding-sibling::Synopsis) + 1"/>
</xsl:variable>

<xsl:variable name="link1"><xsl:value-of select="count(id(@Idref)/ancestor-or-self::*[$ancestors - 1]/preceding-sibling::AboutAuthors) + count(id(@Idref)/ancestor-or-self::*[$ancestors - 1]/preceding-sibling::Introduction) + count(id(@Idref)/ancestor-or-self::*[$ancestors - 1]/preceding-sibling::Chapter) + count(id(@Idref)/ancestor-or-self::*[$ancestors - 1]/preceding-sibling::Appendix) + count(id(@Idref)/ancestor-or-self::*[$ancestors - 1]/preceding-sibling::Glossary) + count(id(@Idref)/ancestor-or-self::*[$ancestors - 1]/preceding-sibling::Index) + 1"/></xsl:variable>

<xsl:variable name="link2">
<xsl:if test="name(id(@Idref)/ancestor-or-self::*[$ancestors - 2])='Section'">
    <xsl:value-of select="$link2refcount"/>
</xsl:if>

<xsl:if test="name(id(@Idref)/ancestor-or-self::*[$ancestors - 2])='Audience'">
    <xsl:value-of select="$link2refcount"/>
</xsl:if>

<xsl:if test="name(id(@Idref)/ancestor-or-self::*[$ancestors - 2])='Synopsis'">
    <xsl:value-of select="$link2refcount"/>
</xsl:if>

<xsl:if test="name(id(@Idref)/ancestor-or-self::*[$ancestors - 2])='SystemReq'">
    <xsl:value-of select="$link2refcount"/>
</xsl:if>

<xsl:if test="name(id(@Idref)/ancestor-or-self::*[$ancestors - 2])='Installation'">
    <xsl:value-of select="$link2refcount"/>
</xsl:if>

<xsl:if test="name(id(@Idref)/ancestor-or-self::*[$ancestors - 2])='Configuration'">
    <xsl:value-of select="$link2refcount"/>
</xsl:if>

<xsl:if test="name(id(@Idref)/ancestor-or-self::*[$ancestors - 2])='DocConventions'">
    <xsl:value-of select="$link2refcount"/>
</xsl:if>
</xsl:variable>


<xsl:variable name="link3">
    <xsl:if test="name(id(@Idref)/ancestor-or-self::*[$ancestors - 3])='Section'">
        <xsl:text>-</xsl:text>
        <xsl:value-of select="count(id(@Idref)/ancestor-or-self::*[$ancestors - 3]/preceding-sibling::Section) + 1"/>
    </xsl:if>
</xsl:variable>

<!-- DP 2/18/2004 This allows transform to cross-reference with Core Help or
    with Product Specific Help e.g. BPEL-->

<!--
<xsl:variable name="crossRefTitle" select="id(@Idref)"/>

<xsl:variable name="coreHelpTopic" select="$coreToc/descendant::node()[@label=$crossRefTitle]"/>

<xsl:variable name="xlinkname">
    <xsl:choose>
        <xsl:when test="$coreHelpTopic">
            <xsl:text>../../</xsl:text><xsl:value-of select="$coreHelpDir"/><xsl:value-of select="$coreHelpTopic/@href"/></xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="$rootname"/><xsl:value-of select="$link1"/><xsl:value-of select="$link2"/><xsl:value-of select="$link3"/>.html</xsl:otherwise>
    </xsl:choose>
</xsl:variable>
-->

<xsl:variable name="xlinkname"><xsl:value-of select="$rootname"/><xsl:value-of select="$link1"/><xsl:value-of select="$link2"/><xsl:value-of select="$link3"/>.html</xsl:variable>

<!--END CALCULATE TARGET FILENAME -->

<!-- OUTPUT CROSS-REFERENCE. Step references output as "step 3"; others use the text from the target paragraph or target child Title element. -->

<xsl:variable name="xreftext">
    <xsl:choose>
        <xsl:when test="contains(@format,'step x')">step <xsl:value-of select="count(id(@Idref)/preceding-sibling::ListItem)+1"/></xsl:when>
        <xsl:when test="@format = 'Table #'"><xsl:value-of select="id(@Idref)"/> table</xsl:when>
        <xsl:when test="@format = 'table x'"><xsl:value-of select="id(@Idref)"/> table</xsl:when>
        <xsl:when test="@format = 'table #'"><xsl:value-of select="id(@Idref)"/> table</xsl:when>
        <xsl:when test="id(@Idref)/Title">
        <xsl:value-of select="normalize-space(id(@Idref)/Title)"/></xsl:when>
        
        <xsl:when test="id(@Idref)/Term"><xsl:value-of 
        select="normalize-space(id(@Idref)/Term)"/></xsl:when>
        
        
        <xsl:otherwise><xsl:value-of select = "id(@Idref)"/></xsl:otherwise>
        
        
    </xsl:choose>
</xsl:variable>

<!-- DP change : this respects the level of transform -->
<!--
<xsl:choose>
    <xsl:when test="$link2!='' and $maxSectionLevel&lt;2"></xsl:when>
    <xsl:when test="$link3!='' and $maxSectionLevel&lt;3"></xsl:when>
    <xsl:otherwise>
        <span class="CrossRef">
            <a href="{$xlinkname}"><xsl:value-of select = "$xreftext"/></a>
            <xsl:if test="@format = 'chapter x, title,'">, </xsl:if>
            <xsl:if test="@format = 'chapter x, title.'">. </xsl:if>
            <xsl:if test="@format = 'appendix x, title,'">, </xsl:if>
            <xsl:if test="@format = 'appendix x, title.'">. </xsl:if>
            <xsl:if test="@format = 'heading,'">, </xsl:if>
            <xsl:if test="@format = 'heading.'">. </xsl:if>
        </span>
    </xsl:otherwise>
</xsl:choose>
-->

    <span class="CrossRef">
        <a href="{$xlinkname}"><xsl:value-of select = "$xreftext"/></a>
        <xsl:if test="@format = 'chapter x, title,'">, </xsl:if>
        <xsl:if test="@format = 'chapter x, title.'">. </xsl:if>
        <xsl:if test="@format = 'appendix x, title,'">, </xsl:if>
        <xsl:if test="@format = 'appendix x, title.'">. </xsl:if>
        <xsl:if test="@format = 'heading,'">, </xsl:if>
        <xsl:if test="@format = 'heading.'">. </xsl:if>
    </span>

</xsl:template>


<!-- GRAPHIC PROCESSING -->


<xsl:template match="Figure">

<p>
<xsl:call-template name="process_filename">
    <xsl:with-param name="file" select="unparsed-entity-uri(GraphicContainer/*/@entity)"/>
</xsl:call-template>

</p>

<p class="Caption">
<xsl:apply-templates/>
</p>
</xsl:template>

<xsl:template name="process_filename">
<xsl:param name="file" select="''"/>


<!-- TESTING HOW TO ADD HEIGHT AND WIDTH ATTRIBUTES TO OUTPUT IMG (Look in XML Cookbook chapter.xsl)
<xsl:variable name="height" select="substring-after(@impsize,' ')"/>
 <xsl:variable name="width" select="substring-before(@impsize,' ')"/>  -->
    
    
    <xsl:choose>
    <xsl:when test="contains($file,'/')">
    <xsl:call-template name="process_filename">
        <xsl:with-param name="file" select="substring-after($file,'/')"/>
    </xsl:call-template>
    </xsl:when>
    <xsl:otherwise> 
<img>       <xsl:attribute name="src">
        <xsl:value-of select="$file"/>
        </xsl:attribute>

        <xsl:attribute name="alt">
        <xsl:value-of select="GraphicContainer/*/@alt"/>
        </xsl:attribute>
        
</img>
    </xsl:otherwise>
    </xsl:choose>

</xsl:template>

<xsl:template name="footer">
<xsl:param name="plink" select="''"/>
<xsl:param name="nlink" select="''"/>

<!-- THIS SECTION OUTPUTS FOOTNOTES AT THE BOTTOM OF THE PAGE. -->
        
    <xsl:for-each select="*[not(self::UserGuide) and not(self::Audience) and not(self::Synopsis) and not(self::SystemReq) and not(self::Installation) and not(self::Configuration) and not(self::DocConventions) and not(self::Section)]//Footnote">
    
        <xsl:if test="position()=1"><br/>
            <br/>
        </xsl:if>
    <p class="Footnote">
    <a name="{count(preceding::Footnote)+1}">
    <xsl:number value="count(preceding::Footnote)+1" format="1"/>
    <xsl:text>. </xsl:text>
    </a>
    <xsl:apply-templates/>
    </p>
    </xsl:for-each> 
    
<!-- BUILD FOOTER -->

<hr color="#9999cc" size="1"/>
<p class="Copyright"><xsl:value-of select="$copyright"/></p>
<!--  Wendy Beren:  COMMENTING OUT TABLE AND PAGE NAVIGATION IN FOOTER

<table border="0" width="100%">
<tr valign="top">
<td class="Copyright" valign="top"><xsl:value-of select="$copyright"/></td>

<td class="NavBarBottom" valign="top">

<xsl:choose>
<xsl:when test="$plink = ''"><xsl:value-of select="$prev_label"/></xsl:when>
<xsl:otherwise><a href="{$plink}"><xsl:value-of select="$prev_label"/></a></xsl:otherwise>
</xsl:choose>
 | 
 
<xsl:choose>
<xsl:when test="'$nlink' != ''"><a href="{$nlink}"><xsl:value-of select="$next_label"/></a></xsl:when>
<xsl:otherwise><xsl:value-of select="$next_label"/></xsl:otherwise>
</xsl:choose>

<br/>

<xsl:choose>
<xsl:when test="$plink != ''"><a href="index.html"><xsl:value-of select="$toc_label"/></a></xsl:when>
<xsl:otherwise><xsl:value-of select="$toc_label"/></xsl:otherwise>
</xsl:choose>
 | 
<xsl:choose>
<xsl:when test="'$nlink' != ''"><a href="{$rootname}{$index_pos}.html"><xsl:value-of select="$ix_label"/></a></xsl:when>
<xsl:otherwise><xsl:value-of select="$ix_label"/></xsl:otherwise>
</xsl:choose>

</td>

</tr>
</table>
Wendy Beren:  END OF COMMENTED SECTION  -->
</xsl:template>


</xsl:stylesheet>
