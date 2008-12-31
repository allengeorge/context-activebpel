<?xml version="1.0"?>

<!-- DocFrame XSL transformation for HTML 4 (CSS) output. Copyright (c) 2003 Scriptorium Publishing Services, Inc.

This XSL transformation file is part of the DocFrame authoring environment. Only licensed users may use or modify this file. Distribution is limited to other DocFrame licensees.

Revision history:
02/18/03    DocFrame 1.0 released
02/20/03    Added copyright statement to top of file
03/18/03    Created a template for hypertext_start and hypertext_end
        and implemented in definitions
03/26/03    Updated templates for titles
03/28/03    Added template for fifth-level headings
11/21/03        wb: REMOVED BANNER IN HEADER TEMPLATE SECTION BY SETTING BACKGROUND COLOR TO WHITE
-->

<xsl:stylesheet version="1.1"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:saxon="http://icl.com/saxon"
    extension-element-prefixes="saxon">
    <xsl:output method="html" encoding="ISO-8859-1"/>
    <xsl:preserve-space elements="Code UserInput"/>
    <xsl:param name="outputDir"/>

    <xsl:include href="html_page_ActiveBPEL_DIV.xsl"/>

    <!-- PARAGRAPH-LEVEL ELEMENTS -->

    <xsl:template name="Id">
        <xsl:if test="@Id">
            <a name="{@Id}"/>
        </xsl:if>
    </xsl:template>

    <xsl:template match="Title">
        <xsl:call-template name="Id"/>
        <h1 class="Title1">
            <xsl:apply-templates/>
        </h1>
    </xsl:template>


    <xsl:template match="/UserGuide/Introduction/*/Title">
        <xsl:call-template name="Id"/>
        <h2 class="Title2">
            <xsl:apply-templates/>
        </h2>
    </xsl:template>

    <xsl:template match="/UserGuide/Chapter/Section/Title">
        <xsl:call-template name="Id"/>
        <h2 class="Title2">
            <xsl:apply-templates/>
        </h2>
    </xsl:template>

    <xsl:template match="/UserGuide/Appendix/Section/Title">
        <xsl:call-template name="Id"/>
        <h2 class="Title2">
            <xsl:apply-templates/>
        </h2>
    </xsl:template>

    <xsl:template match="/UserGuide/*/*/Section/Title">
        <xsl:call-template name="Id"/>
        <h2 class="Title2">
            <xsl:apply-templates/>
        </h2>
    </xsl:template>

    <xsl:template match="/UserGuide/*/*/Section/Section/Title">
        <xsl:call-template name="Id"/>
        <h3 class="Title3">
            <xsl:apply-templates/>
        </h3>
    </xsl:template>

    <xsl:template match="/UserGuide/*/*/Section/Section/Section/Title">
        <xsl:call-template name="Id"/>
        <h4 class="Title4">
            <xsl:apply-templates/>
        </h4>
    </xsl:template>

    <xsl:template match="Introduction">
        <xsl:call-template name="Id"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="Chapter">
        <xsl:call-template name="Id"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="Appendix">
        <xsl:call-template name="Id"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="Glossary">
        <xsl:call-template name="Id"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="AboutAuthors">
        <xsl:call-template name="Id"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="AuthorBio/Author">
        <h2 class="Author">
            <xsl:value-of select="."/>
        </h2>
    </xsl:template>

    <xsl:template match="AuthorBio/Bio">
        <p class="Bio">
            <xsl:value-of select="."/>
        </p>
    </xsl:template>

    <xsl:template name="hypertext_start">
        <xsl:if test="starts-with(Hypertext/@text,'message URL')">
            <xsl:text disable-output-escaping="yes">&lt;a href="</xsl:text>
            <xsl:value-of select="substring-after(Hypertext/@text,'message URL ')"/>
            <xsl:text disable-output-escaping="yes">"&gt;</xsl:text>
        </xsl:if>
        <xsl:if test="starts-with(Hypertext/@text,'message url')">
            <xsl:text disable-output-escaping="yes">&lt;a href="</xsl:text>
            <xsl:value-of select="substring-after(Hypertext/@text,'message url ')"/>
            <xsl:text disable-output-escaping="yes">"&gt;</xsl:text>
        </xsl:if>
        <xsl:if test="starts-with(Hypertext/@text,'toclabel')">
            <xsl:variable name="toclabelText" select="substring-after(Hypertext/@text,'toclabel ')"/>
            <xsl:text disable-output-escaping="yes">&lt;a href="</xsl:text>
            <xsl:value-of select="$coreHelpDir"/>
            <xsl:value-of select="$CoreTocDoc/descendant::node()[@label=$toclabelText]/@href"/>
            <xsl:text disable-output-escaping="yes">"&gt;</xsl:text>
        </xsl:if>
    </xsl:template>

    <xsl:template name="hypertext_end">
        <xsl:if test="starts-with(Hypertext/@text,'message URL')">
            <xsl:text disable-output-escaping="yes">&lt;/a&gt;</xsl:text>
        </xsl:if>
        <xsl:if test="starts-with(Hypertext/@text,'message url')">
            <xsl:text disable-output-escaping="yes">&lt;/a&gt;</xsl:text>
        </xsl:if>
        <xsl:if test="starts-with(Hypertext/@text,'toclabel')">
            <xsl:text disable-output-escaping="yes">&lt;/a&gt;</xsl:text>
        </xsl:if>
    </xsl:template>

    <xsl:template match="Para">
        <xsl:choose>
            <xsl:when test="name(following-sibling::*[1])='TableFootnote'"></xsl:when>
            <xsl:otherwise>
                <p class="Para">
                    <xsl:call-template name="hypertext_start"/>
                    <xsl:apply-templates/>
                    <xsl:call-template name="hypertext_end"/>
                </p>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="List">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="TableList">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="Code">
        <pre class="Code">
            <xsl:call-template name="hypertext_start"/>
            <xsl:apply-templates/>
            <xsl:call-template name="hypertext_end"/>
        </pre>
    </xsl:template>

    <xsl:template match="Note/Para">
        <p class="Para">
            <b>Note: </b>
            <xsl:call-template name="hypertext_start"/>
            <xsl:apply-templates/>
            <xsl:call-template name="hypertext_end"/>
        </p>
    </xsl:template>

    <xsl:template match="Warning">
        <table>
            <tr>
                <td width="40" class="WarningLabel" valign="top">Warning: </td>
                <td class="WarningText">
                    <xsl:apply-templates/>
                </td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template match="WarningBody">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="CautionBody">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="Caution">
        <table>
            <tr>
                <td width="40" class="CautionLabel" valign="top">Caution: </td>
                <td class="CautionText">
                    <xsl:apply-templates/>
                </td>
            </tr>
        </table>
    </xsl:template>


    <xsl:template match="Glossary/Title">
        <h1 class="Title1">
            <xsl:value-of select="."/>
        </h1>
        <xsl:for-each select="../GlossEntry">
            <p class="GlossTOC">
                <a>
                    <xsl:attribute name="href">#<xsl:value-of select="Term"/></xsl:attribute>
                    <xsl:value-of select="Term"/>
                </a>
            </p>
        </xsl:for-each>
    </xsl:template>


    <xsl:template match="GlossEntry">
        <xsl:call-template name="Id"/>
        <xsl:apply-templates/>
    </xsl:template>


    <xsl:template match="Term">
        <h2 class="Term">
            <a>
                <xsl:attribute name="name">
                    <xsl:value-of select="."/>
                </xsl:attribute>
                <xsl:apply-templates/>
            </a>
        </h2>
    </xsl:template>

    <xsl:template match="Definition">
        <p class="Definition">
            <xsl:call-template name="hypertext_start"/>
            <xsl:apply-templates/>
            <xsl:call-template name="hypertext_end"/>
        </p>
    </xsl:template>

    <xsl:template match="Caption">
        <xsl:call-template name="Id"/>
        <xsl:apply-templates/>
    </xsl:template>


    <!--  FOOTNOTES
                 Inserts a superscript footnote number or letter in the paragraph. -->

    <xsl:template match="Footnote">
        <sup>
            <font size="-1">
                <span class="Footnote">
                    <a href="#{count(preceding::Footnote)+1}">
                        <xsl:number value="count(preceding::Footnote)+1" format="1"/>
                        <xsl:value-of select="Footnote"/>
                    </a>
                </span>
            </font>
        </sup>
    </xsl:template>


    <xsl:template match="TableFootnote">
        <p class="Para">
            <xsl:value-of select="preceding-sibling::Para[1]"/>

            <sup>
                <font size="-1">
                    <span class="TableFootnote">
                        <a href="#t{count(preceding::TableFootnote)+1}">
                            <xsl:number value="count(preceding::TableFootnote)+1" format="a"/>

                        </a>
                    </span>
                </font>
            </sup>
        </p>
    </xsl:template>


    <!-- LISTS -->


    <xsl:template match='List[@Type="Bulleted"]'>
        <ul>
            <xsl:apply-templates/>
        </ul>
    </xsl:template>

    <xsl:template match='List[@Type="Numbered"]'>
        <!--  Allow 2 different levels of ordered list items 1-? and a-z    -->
        <ol>
            <xsl:attribute name="type">
                <xsl:choose>
                    <xsl:when test="name(..)='List'or name(..)='TableList'">
                        <xsl:text>a</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>1</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
            <xsl:apply-templates/>
        </ol>
    </xsl:template>

    <xsl:template match='TableList[@Type="Bulleted"]'>
        <ul>
            <xsl:apply-templates/>
        </ul>
    </xsl:template>

    <xsl:template match='TableList[@Type="Numbered"]'>
    <!--  Allow 2 different levels of ordered list items 1-? and a-z    -->
        <ol>
            <xsl:attribute name="type">
                <xsl:choose>
                    <xsl:when test="name(..)='List'or name(..)='TableList'">
                        <xsl:text>a</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>1</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
            <xsl:apply-templates/>
        </ol>
    </xsl:template>

    <xsl:template match="ListItem">
        <xsl:call-template name="Id"/>
        <li class="ListItem">
            <xsl:call-template name="hypertext_start"/>
            <xsl:apply-templates/>
            <xsl:call-template name="hypertext_end"/>
        </li>
    </xsl:template>



    <!-- TABLE ELEMENTS -->
    <xsl:template match="TableTitle">
        <caption class="Caption">
            <xsl:call-template name="Id"/>
            <xsl:apply-templates/>
        </caption>
    </xsl:template>

    <xsl:template match="Table">
        <table cellpadding="3" cellspacing="0">
            <xsl:apply-templates/>
        </table>

        <xsl:for-each select="*/TableRow/TableCell/TableFootnote">
            <xsl:if test="position()=1">
                <hr width="40%" align="left" size="1"/>
            </xsl:if>

            <p class="TableFootnote">
                <a name="t{count(preceding::TableFootnote)+1}">
                    <xsl:number value="count(preceding::TableFootnote)+1" format="a"/>
                    <xsl:text>. </xsl:text>
                </a>
                <xsl:apply-templates/>
            </p>
            <xsl:if test="position() = last()">
                <br/>
                <br/>
            </xsl:if>
        </xsl:for-each>

    </xsl:template>

    <xsl:template match="TableHeading/TableRow">
        <tr>
            <xsl:for-each select="TableCell">
                <th class="TableHeading" valign="top">
                    <xsl:if test="@hstraddle">
                        <xsl:attribute name="colspan">
                            <xsl:value-of select="@hstraddle"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:if test="@vstraddle">
                        <xsl:attribute name="rowspan">
                            <xsl:value-of select="@vstraddle"/>
                        </xsl:attribute>
                    </xsl:if>
                    <xsl:apply-templates/>
                </th>
            </xsl:for-each>
        </tr>
    </xsl:template>

    <xsl:template match="TableCell">
        <td valign="top">
            <xsl:if test="@hstraddle">
                <xsl:attribute name="colspan">
                    <xsl:value-of select="@hstraddle"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:if test="@vstraddle">
                <xsl:attribute name="rowspan">
                    <xsl:value-of select="@vstraddle"/>
                </xsl:attribute>
            </xsl:if>

            <xsl:apply-templates/>
        </td>
    </xsl:template>

    <xsl:template match="TableBody/TableRow">
        <tr>
            <xsl:apply-templates/>
        </tr>
    </xsl:template>




    <!-- INLINE ELEMENTS -->

    <xsl:template match="Emphasis">
        <xsl:call-template name="hypertext_start"/>
        <em class="Emphasis">
            <xsl:apply-templates/>
        </em>
        <xsl:call-template name="hypertext_end"/>
    </xsl:template>

    <xsl:template match="Strong">
        <xsl:call-template name="hypertext_start"/>
        <strong class="Strong">
            <xsl:apply-templates/>
        </strong>
        <xsl:call-template name="hypertext_end"/>
    </xsl:template>

    <xsl:template match="Superscript">
        <sup class="Superscript">
            <xsl:apply-templates/>
        </sup>
    </xsl:template>

    <xsl:template match="UserInput">
        <xsl:call-template name="hypertext_start"/>
        <code class="UserInput">
            <xsl:apply-templates/>
        </code>
        <xsl:call-template name="hypertext_end"/>
    </xsl:template>

    <xsl:template match="MenuItem">
        <xsl:call-template name="hypertext_start"/>
        <strong class="MenuItem">
            <xsl:apply-templates/>
        </strong>
        <xsl:call-template name="hypertext_end"/>
    </xsl:template>

    <xsl:template match="Link">
        <xsl:call-template name="hypertext_start"/>
        <xsl:apply-templates/>
        <xsl:call-template name="hypertext_end"/>
    </xsl:template>


    <!-- HEADER TEMPLATE (SEE HTML_PAGE.XSL FOR FOOTER TEMPLATE) -->
    <!-- REMOVED BANNER BY SETTING BACKGROUND COLOR TO WHITE -->

    <xsl:template name="header">

        <p class="Booktitle"> <xsl:value-of select="$booktitle"/> </p>
                
    </xsl:template>


</xsl:stylesheet>

