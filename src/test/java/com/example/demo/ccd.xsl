<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:n1="urn:hl7-org:v3"
 xmlns:n2="urn:hl7-org:v3/meta/voc" xmlns:n3="http://www.w3.org/1999/xhtml"
 xmlns:voc="urn:hl7-org:v3/voc"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output doctype-public="-//W3C//DTD HTML 4.01//EN" encoding="ISO-8859-1"
  indent="yes" method="html" version="4.01"/>

 <!--<xsl:key name="ObjectIDKey" match="//n1:id" use="n1:root"/>-->


 <!-- CDA document -->
 <xsl:variable name="tableWidth">50%</xsl:variable>

 <xsl:variable name="title">
  <xsl:choose>
   <xsl:when test="/n1:ClinicalDocument/n1:title">
    <xsl:value-of select="/n1:ClinicalDocument/n1:title"/>
   </xsl:when>
   <xsl:otherwise>Clinical Document</xsl:otherwise>
  </xsl:choose>
 </xsl:variable>


 <xsl:template match="/n1:ClinicalDocument">
  <html>
   <head>
    <!--    <style type="text/css" media="screen">@import "PPccr.css";</style>-->
    <title xml:space="preserve">
            <xsl:apply-templates select="n1:recordTarget/n1:patientRole/n1:patient/n1:name"/> - <xsl:value-of select="$title"/>
        </title>
    <style type="text/css">

body {
    margin: 0 0 0 0;
    padding: 0 0 0 0;
    border-width: 0 0 0 0;
    border-collapse: collapse;
    background: url('data:image/png;base64,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') no-repeat fixed transparent;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: 100% 100%;
}

table.first {
    margin: 0 0 0 0;
    padding: 0 0 0 0;
    border-width: 0 0 0 0;
    text-align: left;
    vertical-align: top;
    border-color: #d0d0d0;
    background-color: transparent;
    font: 90% tahoma,arial,verdana,sans-serif;
    border-collapse: collapse 
}

table.second {
    color: #5b5b5b;
    margin: 0 0 0 0;
    padding: 0 0 0 0;
    text-align: left;
    vertical-align: top;
    border-width: 0 0 0 0;
    border-color: #d0d0d0;
    background-color: transparent;
    font: 95% tahoma,arial,verdana,sans-serif;
    border-collapse: collapse 
}

th.first {
    text-align: left;
    vertical-align: top;
    color: white;
    background-color: #942428;
    font: bold 175% tahoma,arial,verdana,sans-serif;
    padding-left: 3px;
    padding-right: 3px;
    border-collapse: collapse 
}

th.second {
    text-align: left;
    vertical-align: top;
    color: white;
    background-color: #D53339;
    font: bold    165% sans-serif;
    padding-left: 5px;
    padding-right: 3px;
    border-collapse: collapse 
}

th.toc {
    text-align: right;
    vertical-align: top;
    color: white;
    background-color: #00346B;
    font: bold sans-serif;
    padding-left: 5px;
    padding-right: 3px;
    border-collapse: collapse 
}

th.third {
    text-align: left;
    vertical-align: top;
    color: white;
    background-color: #D53339;

    font: bold 140% sans-serif;
    padding-left: 7px;
    padding-right: 3px;
    border-collapse: collapse 
}

th.fourth {
    text-align: left;
    vertical-align: top;
    color: #5b5b5b;
    background-color: rgba(250,250,250,.8);
    font: bold 115% sans-serif;
    padding-left: 9px;
    padding-right: 3px;
    border-collapse: collapse 
}

th.fifth {
    text-align: left;
    vertical-align: top;
    color: #5b5b5b;
    background-color: rgba(250,250,250,.8);
    font: bold 100% tahoma,arial,verdana,sans-serif;
    padding-left: 9px;
    padding-right: 3px;
    border-collapse: collapse 
}

th.content {
 text-align: left;
 vertical-align: top;
 padding-top: 2px;
 padding-bottom: 2px;
 padding-left: 9px;
 padding-right: 3px;
 border-collapse: collapse 
}


thead.fourth {
    color: #5b5b5b;
    margin: 0 0 0 0;
    padding: 0 0 0 0;
    text-align: left;
    vertical-align: top;
    border-width: 2px 2px 2px 2px;
    background-color: rgba(250,250,250,.8);
    font: bold 115% tahoma,arial,verdana,sans-serif;
    border-collapse: collapse 
}

thead.fifth {
    text-align: left;
    vertical-align: top;
    color: #5b5b5b;
    background-color: rgba(250,250,250,.8);
    font: bold 100% tahoma,arial,verdana,sans-serif;
    padding-left: 9px;
    padding-right: 3px;
    border-collapse: collapse 
}

tr.first {
    text-align: left;
    vertical-align: top;
    color: #5b5b5b;
    background-color: rgba(250,250,250,.8);
    padding-top: 3px;
    padding-bottom: 3px;
    padding-left: 9px;
    padding-right: 3px;
    border-collapse: collapse 
}

tr.second {
    text-align: left;
    vertical-align: top;
    color: #5b5b5b;
    background-color: rgba(250,250,250,.8);
    padding-top: 3px;
    padding-bottom: 3px;
    padding-left: 9px;
    padding-right: 3px;
    border-collapse: collapse 
}


tr.content {
    text-align: left;
    vertical-align: top;
    padding-top: 2px;
    padding-bottom: 2px;
    padding-left: 9px;
    padding-right: 3px;
    border-collapse: collapse;
}

td.content  {
    padding-left: 9px;
    padding-right: 3px;
    padding-top: 2px;
    padding-bottom: 5px;
}

a.first 
{
    border-width: 0px;
    text-decoration: none;
    text-align: right;
    color: white;    
}


#smenu {
    z-index: 1;
    position: absolute;
    top: 45px;
    left: 685px;
    width: 100%;
    float: left;
    text-align: right;
    color: #000;
}
</style>

    <style type="text/css">
#menu {
    position: absolute;
    top: 45px;
    left: 0px;
    z-index: 1;
    float: left;
    text-align: right;
    color: #000;
    list-style: none;
    line-height: 1;
}
</style>

    <xsl:comment><![CDATA[[if lt IE 7]>
<style type="text/css">
#menu {
    display: none;
}
</style>
<![endif]]]></xsl:comment>

    <style type="text/css">

#menu ul {
    list-style: none;
    margin: 0;
    padding: 0;
    width: 12em;
    float: right;
    text-align: right;
    color: #000;
}

#menu a, #menu h2 {
    font: bold 11px/16px arial, helvetica, tahoma,arial,verdana,sans-serif;
    text-align: right;
    display: block;
    border-width: 0px;
    border-style: solid;
    border-color: #ccc #888 #555 #bbb;
    margin: 0;
    padding: 2px 3px;
    color: #000;
}

#menu h2 {
    color: #fff;
    text-transform: uppercase;
    text-align: right;
}

#menu a {
    text-decoration: none;
    text-align: right;
    border-width: 1px;
    border-style: solid;
    border-color: #fff #777 #777 #777;
}

#menu a:hover {
    color: #fff;
    background: #D53339;
    text-align: right;
}

#menu li {
    position: relative;
}

#menu ul ul {
    position: relative;
    z-index: 500;
    text-align: left;
    color: #000;
    background-color: #E0E5E5;
    float: right;
}

#menu ul ul ul {
    position: absolute;
    top: 0;
    left: 100%;
    text-align: right;
    float: right;
}

div#menu ul ul,
div#menu ul li:hover ul ul,
div#menu ul ul li:hover ul ul
{display: none;}

div#menu ul li:hover ul,
div#menu ul ul li:hover ul,
div#menu ul ul ul li:hover ul
{display: block;}

</style>

   </head>
   <body xml:space="preserve">

<!--<center><img src="http://www.pmsi.com/images/banner.jpg"/></center><p/>-->
<center>
<!-- table of contents menu -->
<div id="menu"><center>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td>&#160;</td>
            <td width="800">
                <ul>
                    <li><h2>TOC [<font color="#fffacd">+</font>]</h2>
                        <ul>
                            <xsl:for-each select="n1:component">
                                <xsl:for-each select="n1:structuredBody">
                                    <xsl:for-each select="n1:component">
                                        <xsl:for-each select="n1:section">
                                        <li><nobr><a><xsl:attribute name="href">#<xsl:value-of select="n1:title"/></xsl:attribute><xsl:value-of select="n1:title"/></a></nobr>
                                        <!-- submenus removed until positioning works
                                            <li><nobr><a><xsl:attribute name="href">#<xsl:value-of select="n1:title"/></xsl:attribute><xsl:value-of select="n1:title"/><xsl:if test="count(n1:component/n1:section)&gt;0">...</xsl:if></a></nobr>
                                            
                                                <xsl:if test="count(n1:component/n1:section)&gt;0">
                                                    <ul>
                                                        <xsl:for-each select="n1:component">
                                                            <xsl:for-each select="n1:section">
                                                                <li><nobr><a><xsl:attribute name="href">#<xsl:value-of select="n1:title"/></xsl:attribute><xsl:value-of select="n1:title"/></a></nobr></li>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </ul>
                                                </xsl:if>
                                            -->
                                            </li>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </xsl:for-each>
                            </xsl:for-each>
                        </ul>
                    </li>
                </ul>
            </td>
            <td>&#160;</td>
        </tr>
    </table>
</center>
</div>
<table border="1" cellpadding="0" cellspacing="0" class="first" width="800">
    <th class="first">
        <center><nobr><a name="top"><xsl:apply-templates select="n1:recordTarget/n1:patientRole/n1:patient/n1:name"/> - <xsl:value-of select="$title"/></a></nobr></center>
    </th>
<!--Document Information -->
    <tr>
        <td>
            <table border="0" cellpadding="0" cellspacing="0" class="second" width="100%">
                <th class="second" colspan="2">
                    <table>
                        <tr>
                            <td><nobr><xsl:text>Document Information</xsl:text></nobr></td>
                <td class="toc">
                    <!--<div id="menu">
                        <ul>
                            <li><h2>TOC [+]</h2>
                                <ul>
                                    <xsl:for-each select="n1:component">
                                        <xsl:for-each select="n1:structuredBody">
                                            <xsl:for-each select="n1:component">
                                                <xsl:for-each select="n1:section">
                                                    <li><nobr><a href=""><xsl:value-of select="n1:title"/></a></nobr></li>
                                                </xsl:for-each>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </ul>
                            </li>
                        </ul>
                    </div>-->
                </td>
                </tr></table></th>
                
                <tr>
                    <td height="100%" valign="top" width="50%">
                        <table border="0" class="second" height="100%" width="100%">
                            <th class="fourth" colspan="2"><xsl:text>Authored By:</xsl:text></th>
                            <xsl:for-each select="n1:author/n1:assignedAuthor">
                                <xsl:if test="n1:assignedPerson/n1:name!=''">
                                    <tr class="content">
                                        <td class="content">
                                            <b><xsl:text>Name:</xsl:text></b>
                                        </td>
                                        <td class="content">
                                            <xsl:apply-templates select="n1:assignedPerson/n1:name"/>
                                        </td>
                                    </tr>
                                </xsl:if>
                                <xsl:if test="n1:addr!=''">
                                    <tr class="content">
                                        <td class="content">
                                            <b><xsl:text>Address:</xsl:text></b>
                                        </td>
                                        <td class="content">
                                            <xsl:apply-templates select="n1:addr"/>
                                        </td>
                                    </tr>
                                </xsl:if>
                                <xsl:for-each select="n1:telecom">
                                    <tr class="content">
                                        <td class="content">
                                            <nobr><b><xsl:apply-templates mode="Label" select="."/>:</b></nobr>
                                        </td>
                                        <td class="content">
                                            <nobr><xsl:apply-templates select="."/></nobr>
                                        </td>
                                    </tr>
                                </xsl:for-each>
<!--
                                <xsl:if test="CCR:Organization/CCR:Name!=''">
                                    <tr class="content">
                                        <td>
                                            <b><xsl:text>Organization:</xsl:text></b>
                                        </td>
                                        <td>
                                            <xsl:value-of select="CCR:Organization/CCR:Name"/>
                                        </td>
                                    </tr>
                                </xsl:if>
-->
                                <xsl:if test="n1:assignedAuthoringDevice!=''">
                                    <tr class="content">
                                        <td class="content">
                                            <nobr><b><xsl:text>System:</xsl:text></b></nobr>
                                        </td>
                                        <td class="content">
                                            <xsl:apply-templates mode="DisplayGiven" select="n1:assignedAuthoringDevice/n1:manufacturerModelName"/><xsl:apply-templates mode="DisplayGiven" select="n1:assignedAuthoringDevice/n1:softwareName"/>
                                        </td>
                                    </tr>
                                </xsl:if>
                            </xsl:for-each>
                            <xsl:if test="/n1:ClinicalDocument/n1:legalAuthenticator/n1:assignedEntity/n1:assignedPerson/n1:name!=''">
                                <tr class="content">
                                    <td class="content">
                                        <nobr><b><xsl:text>Signed by:</xsl:text></b></nobr>
                                    </td>
                                    <td class="content">
                                        <xsl:apply-templates select="/n1:ClinicalDocument/n1:legalAuthenticator/n1:assignedEntity/n1:assignedPerson/n1:name"/>
                                        <xsl:text> on </xsl:text>
                                        <xsl:apply-templates mode="Date" select="//n1:ClinicalDocument/n1:legalAuthenticator/n1:time"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            
                            <tr height="100%"><td/></tr>
                        </table>
                    </td>
                    <td height="100%" valign="top" width="50%">
                        <table border="0" class="second" height="100%" width="100%">
                            <th class="fourth" colspan="2"><xsl:text>Detail:</xsl:text></th>
                            <xsl:if test="n1:title!=''">
                                <tr class="content">
                                    <td class="content">
                                        <b><xsl:text>Title:</xsl:text></b>
                                    </td>
                                    <td class="content">
                                        <xsl:apply-templates select="n1:title"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="count(n1:code)&gt;0">
                                <tr class="content">
                                    <td class="content">
                                        <b>Description<xsl:if test="count(n1:code)&gt;1">s</xsl:if>:</b>
                                    </td>
                                    <td class="content">
                                        <xsl:for-each select="n1:code">
                                            <xsl:apply-templates mode="CE" select="."/><br/>
                                        </xsl:for-each>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:if test="n1:effectiveTime/@value!=''">
                                <tr class="content">
                                    <td class="content">
                                        <b><nobr><xsl:text>Effective Date:</xsl:text></nobr></b>
                                    </td>
                                    <td class="content">
                                        <xsl:apply-templates mode="Date" select="n1:effectiveTime"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <xsl:for-each select="n1:telecom">
                                <tr class="content">
                                    <td class="content">
                                        <nobr><b><xsl:apply-templates mode="Label" select="."/>:</b></nobr>
                                    </td>
                                    <td class="content">
                                        <nobr><xsl:apply-templates select="."/></nobr>
                                    </td>
                                </tr>
                            </xsl:for-each>
                            <xsl:if test="n1:assignedAuthoringDevice!=''">
                                <tr class="content">
                                    <td class="content">
                                        <nobr><b><xsl:text>System:</xsl:text></b></nobr>
                                    </td>
                                    <td class="content">
                                        <xsl:apply-templates mode="DisplayGiven" select="n1:assignedAuthoringDevice/n1:manufacturerModelName"/><xsl:apply-templates mode="DisplayGiven" select="n1:assignedAuthoringDevice/n1:softwareName"/>
                                    </td>
                                </tr>
                            </xsl:if>
                            <tr height="100%"><td/></tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

<!--Patient Information -->
    <tr>
        <td>
            <table cellpadding="0" cellspacing="0" class="second" width="100%">
                <th class="second" colspan="3"><nobr><xsl:text>Patient Information</xsl:text></nobr></th>
                <tr>
                    <td>
                        <table cellpadding="0" cellspacing="0" class="second" width="100%">
                            <th class="third" colspan="3"><nobr><xsl:text>Patient Detail</xsl:text></nobr></th>
                            <xsl:for-each select="n1:recordTarget/n1:patientRole">
                                <tr valign="top">
                                    <td width="50%">
                                        <table border="0" cellpadding="0" cellspacing="0" class="second" valign="top" width="100%">
                                        <tr class="content" valign="top">
                                            <td class="content">
                                                <b><xsl:text>Name:</xsl:text></b>
                                            </td>
                                            <td class="content" width="100%">
                                                <xsl:apply-templates select="n1:patient/n1:name"/>
                                            </td>
                                        </tr>
                                        <xsl:for-each select="n1:addr">
                                            <tr class="content">
                                                <td class="content">
                                                    <b>Address:</b>
                                                </td>
                                                <td class="content" width="100%">
                                                    <xsl:apply-templates select="."/>
                                                </td>
                                            </tr>
                                        </xsl:for-each>
                                        <xsl:for-each select="n1:telecom">
                                            <tr class="content">
                                                <td class="content">
                                                    <nobr><b><xsl:apply-templates mode="Label" select="."/></b></nobr>
                                                </td>
                                                <td class="content">
                                                    <nobr><xsl:apply-templates select="."/></nobr>
                                                </td>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </td>
                                <td width="50%">
                                    <table border="0" cellpadding="0" cellspacing="0" class="second" width="100%">
                                        <xsl:if test="n1:patient/n1:birthTime/@value!=''">
                                            <tr class="content">
                                                <td class="content">
                                                    <b><nobr><xsl:text>Date of Birth:</xsl:text></nobr></b>
                                                </td>
                                                <td class="content" width="100%">
                                                    <nobr><xsl:apply-templates mode="Date" select="n1:patient/n1:birthTime"/></nobr>
                                                </td>
                                            </tr>
                                        </xsl:if>
                                        <xsl:if test="n1:patient/n1:administrativeGenderCode/@code!=''">
                                            <tr class="content">
                                                <td class="content">
                                                    <b><xsl:text>Gender:</xsl:text></b>
                                                </td>
                                                <td class="content" width="100%">
                                                    <xsl:apply-templates mode="Gender" select="n1:patient/n1:administrativeGenderCode"/>
                                                </td>
                                            </tr>
                                        </xsl:if>
                                        <xsl:if test="n1:patient/n1:raceCode!=''">
                                            <tr class="content">
                                                <td class="content">
                                                    <b><xsl:text>Race:</xsl:text></b>
                                                </td>
                                                <td class="content" width="100%">
                                                    <xsl:apply-templates mode="CE" select="n1:patient/n1:raceCode"/>
                                                </td>
                                            </tr>
                                        </xsl:if>
                                        <xsl:if test="n1:patient/n1:ethnicGroupCode!=''">
                                            <tr class="content">
                                                <td class="content">
                                                    <b><xsl:text>Ethnicity:</xsl:text></b>
                                                </td>
                                                <td class="content" width="100%">
                                                    <xsl:apply-templates mode="CE" select="n1:patient/n1:ethnicGroupCode"/>
                                                </td>
                                            </tr>
                                        </xsl:if>
                                        <xsl:if test="n1:patient/n1:languageCommunication!=''">
                                            <tr class="content">
                                                <td class="content">
                                                        <b><xsl:text>Language:</xsl:text></b>
                                                </td>
                                                <td class="content" width="100%">
                                                    <xsl:apply-templates mode="LanguageCommunication" select="n1:patient/n1:languageCommunication"/>
                                                </td>
                                            </tr>
                                        </xsl:if>
                                    </table>
                                </td>
                                <td width="33%">
                                    <table border="0" cellpadding="0" cellspacing="0" class="second" width="100%">
                                        <xsl:for-each select="id">
                                            <tr class="content">
                                                <td>
                                                    <b><nobr><xsl:value-of select="@root"/>:</nobr></b>
                                                </td>
                                                <td width="100%">
                                                    <nobr><xsl:value-of select="@extension"/></nobr>
                                                </td>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </td>
                            </tr>        
                        </xsl:for-each>

                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    
 <!-- 
********************************************************
  CDA Body
********************************************************
  --> 
  <xsl:apply-templates select="n1:component/n1:structuredBody"/>
</table>
</center>
</body>
  </html>
 </xsl:template>

 <!-- StructuredBody -->
 <xsl:template match="n1:component/n1:structuredBody">
  <xsl:apply-templates select="n1:component/n1:section"/>
 </xsl:template>

 <!-- Component/Section -->
 <xsl:template match="n1:component/n1:section">
  <tr>
   <td>
    <xsl:variable name="tableClass">
     <xsl:choose>
      <xsl:when test="count(ancestor::n1:component/n1:section)=1">second</xsl:when>
      <xsl:when test="count(ancestor::n1:component/n1:section)=2">third</xsl:when>
      <xsl:when test="count(ancestor::n1:component/n1:section)=3"
      >fourth</xsl:when>
     </xsl:choose>
    </xsl:variable>
    <xsl:variable name="thClass">
     <xsl:choose>
      <xsl:when test="count(ancestor::n1:component/n1:section)=1">third</xsl:when>
      <xsl:when test="count(ancestor::n1:component/n1:section)=2">fourth</xsl:when>
      <xsl:when test="count(ancestor::n1:component/n1:section)=3"
      >fifth</xsl:when>
     </xsl:choose>
    </xsl:variable>
    <table cellpadding="0" cellspacing="0" width="100%">
     <xsl:attribute name="class">
      <xsl:value-of select="$tableClass"/>
     </xsl:attribute>
     <th width="100%">
      <xsl:attribute name="class">
       <xsl:value-of select="$thClass"/>
      </xsl:attribute>
      <nobr>
       <a>
        <xsl:attribute name="name">
         <xsl:value-of select="n1:title"/>
        </xsl:attribute>
       </a>
       <xsl:value-of select="n1:title"/>
      </nobr>
     </th>
     <th>
      <xsl:attribute name="class">
       <xsl:value-of select="$thClass"/>
      </xsl:attribute>
      <small>
       <a class="first" href="#top" title="Top of page">
        <font color="#fffacd">^</font>
       </a>
      </small>
     </th>
     <tr>
      <td colspan="2">
       <!--<xsl:choose>
                    <xsl:when test="count(n1:entry)=0">-->
       <xsl:apply-templates select="n1:text"/>
       <!--</xsl:when>
                    <xsl:otherwise>
                        <xsl:apply-templates select="n1:entry"/>
                    </xsl:otherwise>
                </xsl:choose>-->
      </td>
     </tr>
    </table>
   </td>
  </tr>

  <xsl:apply-templates select="n1:component/n1:section"/>
 </xsl:template>


 <xsl:template match="n1:name">
  <xsl:apply-templates mode="DisplayGiven" select="n1:prefix"/>
  <xsl:apply-templates mode="DisplayGiven" select="n1:given"/>
  <xsl:apply-templates mode="DisplayGiven" select="n1:family"/>
  <xsl:apply-templates mode="DisplayGiven" select="n1:suffix"/>
 </xsl:template>

 <xsl:template match="n1:addr">
  <xsl:choose>
   <xsl:when test="count(*)=0">
    <xsl:value-of select="."/>
   </xsl:when>
   <xsl:otherwise>
    <xsl:if test="n1:careOf!=''">
     <nobr>
      <xsl:value-of select="n1:careOf"/>
     </nobr>
     <br/>
    </xsl:if>
    <xsl:for-each select="n1:streetAddressLine">
     <nobr>
      <xsl:value-of select="."/>
     </nobr>
     <br/>
    </xsl:for-each>
    <xsl:if test="n1:houseNumber!=''">
     <nobr>
      <xsl:apply-templates mode="DisplayGiven" select="n1:houseNumber"/>
      <xsl:apply-templates mode="DisplayGiven" select="n1:direction"/>
      <xsl:apply-templates mode="DisplayGiven" select="n1:streetName"/>
     </nobr>
     <br/>
    </xsl:if>
    <xsl:if test="n1:city!=''">
     <nobr><xsl:value-of select="n1:city"/>, <xsl:apply-templates
       mode="DisplayGiven" select="n1:state"/><xsl:apply-templates
       mode="DisplayGiven" select="n1:postalCode"/></nobr>
     <br/>
    </xsl:if>
    <xsl:if test="n1:country!=''">
     <nobr>
      <xsl:value-of select="n1:country"/>
     </nobr>
     <br/>
    </xsl:if>
   </xsl:otherwise>
  </xsl:choose>
 </xsl:template>

 <xsl:template match="*" mode="Date">
  <xsl:choose>
   <!-- date only -->
   <xsl:when test="string-length(@value)=8">
    <xsl:apply-templates mode="DateDisplay" select="."/>
   </xsl:when>
   <!-- date + time (may include time zone which is not processed) -->
   <xsl:when test="string-length(@value)&gt;11">
    <xsl:apply-templates mode="DateDisplay" select="."/>
    <xsl:text> </xsl:text>
    <nobr>at <xsl:apply-templates mode="TimeDisplay" select="."/>
     <xsl:if test="substring(@value, 15, 1)='-' or substring(@value, 15, 1)='+'">
      <xsl:apply-templates mode="TimeZoneDisplay" select="."/>
     </xsl:if></nobr>
   </xsl:when>
   <!-- unknown format -->
   <xsl:otherwise>
    <xsl:value-of select="."/>
   </xsl:otherwise>
  </xsl:choose>
 </xsl:template>

 <xsl:template match="*" mode="DateDisplay">
  <xsl:apply-templates mode="DayOfWeekDisplay" select="."/>
  <xsl:text>, </xsl:text>
  <xsl:variable name="month" select="substring(@value, 5, 2)"/>
  <xsl:choose>
   <xsl:when test="$month='01'">
    <xsl:text>January </xsl:text>
   </xsl:when>
   <xsl:when test="$month='02'">
    <xsl:text>February </xsl:text>
   </xsl:when>
   <xsl:when test="$month='03'">
    <xsl:text>March </xsl:text>
   </xsl:when>
   <xsl:when test="$month='04'">
    <xsl:text>April </xsl:text>
   </xsl:when>
   <xsl:when test="$month='05'">
    <xsl:text>May </xsl:text>
   </xsl:when>
   <xsl:when test="$month='06'">
    <xsl:text>June </xsl:text>
   </xsl:when>
   <xsl:when test="$month='07'">
    <xsl:text>July </xsl:text>
   </xsl:when>
   <xsl:when test="$month='08'">
    <xsl:text>August </xsl:text>
   </xsl:when>
   <xsl:when test="$month='09'">
    <xsl:text>September </xsl:text>
   </xsl:when>
   <xsl:when test="$month='10'">
    <xsl:text>October </xsl:text>
   </xsl:when>
   <xsl:when test="$month='11'">
    <xsl:text>November </xsl:text>
   </xsl:when>
   <xsl:when test="$month='12'">
    <xsl:text>December </xsl:text>
   </xsl:when>
  </xsl:choose>
  <xsl:choose>
   <xsl:when test="substring(@value, 7, 1)=&quot;0&quot;">
    <xsl:value-of select="substring(@value, 8, 1)"/>
    <xsl:text>, </xsl:text>
   </xsl:when>
   <xsl:otherwise>
    <xsl:value-of select="substring(@value, 7, 2)"/>
    <xsl:text>, </xsl:text>
   </xsl:otherwise>
  </xsl:choose>
  <xsl:value-of select="substring(@value, 1, 4)"/>
 </xsl:template>

 <xsl:template match="*" mode="DayOfWeekDisplay">
  <!-- Using Zeller's Rule -->
  <xsl:variable name="Month">
   <xsl:choose>
    <xsl:when test="substring(@value, 5, 2)&lt;3">
     <xsl:value-of select="substring(@value, 5, 2) + 10"/>
    </xsl:when>
    <xsl:otherwise>
     <xsl:value-of select="substring(@value, 5, 2) - 2"/>
    </xsl:otherwise>
   </xsl:choose>
  </xsl:variable>
  <xsl:variable name="Century">
   <xsl:choose>
    <xsl:when test="$Month&gt;10">
     <xsl:value-of select="substring(substring(@value, 1, 4) - 1, 1, 2)"/>
    </xsl:when>
    <xsl:otherwise>
     <xsl:value-of select="substring(@value, 1, 2)"/>
    </xsl:otherwise>
   </xsl:choose>
  </xsl:variable>
  <xsl:variable name="Year">
   <xsl:choose>
    <xsl:when test="$Month&gt;10">
     <xsl:value-of select="substring(substring(@value, 1, 4) - 1, 3, 2)"/>
    </xsl:when>
    <xsl:otherwise>
     <xsl:value-of select="substring(@value, 3, 2)"/>
    </xsl:otherwise>
   </xsl:choose>
  </xsl:variable>
  <xsl:variable name="Day" select="substring(@value, 7, 2)"/>

  <xsl:variable name="DayOfWeek">
   <xsl:choose>
    <xsl:when
     test="($Day + floor((13 * $Month - 1) div 5) + $Year + floor($Year div 4) + floor($Century div 4) - (2 * $Century)) mod 7&lt;0">
     <xsl:value-of
      select="($Day + floor((13 * $Month - 1) div 5) + $Year + floor($Year div 4) + floor($Century div 4) - (2 * $Century)) mod 7+7"
     />
    </xsl:when>
    <xsl:otherwise>
     <xsl:value-of
      select="($Day + floor((13 * $Month - 1) div 5) + $Year + floor($Year div 4) + floor($Century div 4) - (2 * $Century)) mod 7"
     />
    </xsl:otherwise>
   </xsl:choose>
  </xsl:variable>
  <xsl:choose>
   <xsl:when test="$DayOfWeek=0">Sunday</xsl:when>
   <xsl:when test="$DayOfWeek=1">Monday</xsl:when>
   <xsl:when test="$DayOfWeek=2">Tuesday</xsl:when>
   <xsl:when test="$DayOfWeek=3">Wednesday</xsl:when>
   <xsl:when test="$DayOfWeek=4">Thursday</xsl:when>
   <xsl:when test="$DayOfWeek=5">Friday</xsl:when>
   <xsl:when test="$DayOfWeek=6">Saturday</xsl:when>
  </xsl:choose>
 </xsl:template>

 <xsl:template match="*" mode="TimeDisplay">
  <xsl:variable name="Hours">
   <xsl:choose>
    <xsl:when test="substring(@value, 9, 2)=0">12</xsl:when>
    <xsl:when test="substring(@value, 9, 2)&gt;12">
     <xsl:value-of select="substring(@value, 9, 2)-12"/>
    </xsl:when>
    <xsl:when test="substring(@value, 9, 1)=0">
     <xsl:value-of select="substring(@value, 10, 1)"/>
    </xsl:when>
    <xsl:otherwise>
     <xsl:value-of select="substring(@value, 9, 2)"/>
    </xsl:otherwise>
   </xsl:choose>
  </xsl:variable>
  <xsl:variable name="Meridian">
   <xsl:choose>
    <xsl:when test="substring(@value, 9, 2)&gt;11"> pm</xsl:when>
    <xsl:otherwise> am</xsl:otherwise>
   </xsl:choose>
  </xsl:variable>
  <xsl:value-of select="$Hours"/>:<xsl:value-of
   select="substring(@value, 11, 2)"/><xsl:if
   test="string-length(@value)&gt;5">:<xsl:value-of
    select="substring(@value, 13, 2)"/></xsl:if><xsl:value-of select="$Meridian"
  />
 </xsl:template>

 <xsl:template match="*" mode="TimeZoneDisplay">
  <xsl:text> </xsl:text>
  <small>
   <i>(<xsl:value-of select="substring(@value, 15, string-length(@value) - 14)"
    />)</i>
  </small>
 </xsl:template>

 <xsl:template match="n1:telecom">
  <xsl:choose>
   <xsl:when test="substring(@value, 4, 1)=':'">
    <xsl:value-of select="substring(@value, 5, string-length(@value) - 4)"/>
   </xsl:when>
   <xsl:otherwise>
    <xsl:value-of select="@value"/>
   </xsl:otherwise>
  </xsl:choose>
 </xsl:template>

 <xsl:template match="n1:telecom" mode="Label">
  <xsl:variable name="Postfix">
   <xsl:choose>
    <xsl:when test="substring(@value, 1, 3)='tel' and @use!='PG'">Telephone</xsl:when>
    <xsl:when test="substring(@value, 1, 3)='fax'">Fax</xsl:when>
   </xsl:choose>
  </xsl:variable>
  <nobr>
   <xsl:choose>
    <xsl:when test="@use='H'">Home</xsl:when>
    <xsl:when test="@use='HP'">Home</xsl:when>
    <xsl:when test="@use='HV'">Vacation Home</xsl:when>
    <xsl:when test="@use='WP'">Work</xsl:when>
    <xsl:when test="@use='DIR'">Direct</xsl:when>
    <xsl:when test="@use='BAD'">Bad</xsl:when>
    <xsl:when test="@use='TMP'">Temporary</xsl:when>
    <xsl:when test="@use='AS'">Answering Service</xsl:when>
    <xsl:when test="@use='EC'">Emergency</xsl:when>
    <xsl:when test="@use='MC'">Mobile</xsl:when>
    <xsl:when test="@use='PG'">Pager</xsl:when>
   </xsl:choose>
   <xsl:text> </xsl:text>
   <xsl:value-of select="$Postfix"/>
  </nobr>
 </xsl:template>


 <xsl:template match="*" mode="CE">
  <xsl:choose>
   <xsl:when test="n1:originalText!=''">
    <xsl:value-of select="n1:originalText"/>
   </xsl:when>
   <xsl:when test="@code!='' or @displayName!=''">
    <xsl:choose>
     <xsl:when test="@displayName!=''">
      <nobr>
       <xsl:value-of select="@displayName"/>
       <xsl:text> </xsl:text>
      </nobr>
      <xsl:if test="@code!=''">
       <small>
        <i>
         <nobr>(<xsl:apply-templates mode="DisplayGiven" select="@code"
           /><xsl:text> </xsl:text><xsl:value-of select="@codeSystemName"
         />)</nobr>
        </i>
       </small>
      </xsl:if>
     </xsl:when>
     <xsl:otherwise>
      <xsl:value-of select="@code"/>
      <xsl:value-of select="@codeSystemName"/>
     </xsl:otherwise>
    </xsl:choose>
   </xsl:when>
   <xsl:otherwise>
    <xsl:value-of select="."/>
   </xsl:otherwise>
  </xsl:choose>
 </xsl:template>

 <xsl:template match="*" mode="Gender">
  <xsl:choose>
   <xsl:when test="@code='M'">Male</xsl:when>
   <xsl:when test="@code='F'">Female</xsl:when>
   <xsl:otherwise>
    <xsl:value-of select="@code"/>
   </xsl:otherwise>
  </xsl:choose>
 </xsl:template>


 <xsl:template match="*" mode="LanguageCommunication">
  <xsl:value-of select="languageCode"/>
 </xsl:template>



 <xsl:template match="*" mode="componentEntry"> </xsl:template>


 <xsl:template match="*" mode="DisplayGiven">
  <xsl:if test=".">
   <xsl:value-of select="."/>
   <xsl:text> </xsl:text>
  </xsl:if>
 </xsl:template>


 <!-- Component text rendering -->
 <!--   Text   -->
 <xsl:template match="n1:text" mode="componentText">
  <xsl:apply-templates/>
  <br/>
 </xsl:template>

 <!--      Tables   -->
 <xsl:template
  match="n1:table/@*|n1:thead/@*|n1:tfoot/@*|n1:tbody/@*|n1:colgroup/@*|n1:col/@*|n1:tr/@*|n1:th/@*|n1:td/@*">
  <xsl:copy>
   <xsl:apply-templates/>
  </xsl:copy>
 </xsl:template>

 <xsl:template match="n1:table">
  <table class="second" width="100%">
   <xsl:apply-templates/>
  </table>
 </xsl:template>

 <xsl:template match="n1:thead">
  <thead class="fifth">
   <xsl:apply-templates/>
  </thead>
 </xsl:template>

 <xsl:template match="n1:tfoot">
  <tfoot>
   <xsl:apply-templates/>
  </tfoot>
 </xsl:template>

 <xsl:template match="n1:tbody">
  <tbody>
   <xsl:apply-templates/>
  </tbody>
 </xsl:template>

 <xsl:template match="n1:colgroup">
  <colgroup>
   <xsl:apply-templates/>
  </colgroup>
 </xsl:template>

 <xsl:template match="n1:col">
  <col>
   <xsl:apply-templates/>
  </col>
 </xsl:template>

 <xsl:template match="n1:tr">
  <xsl:variable name="Class">
   <xsl:if test="name(parent::node())!='thead'">
    <xsl:choose>
     <xsl:when test="position() mod 2 = 0">first</xsl:when>
     <xsl:otherwise>second</xsl:otherwise>
    </xsl:choose>
   </xsl:if>
  </xsl:variable>
  <tr class="{$Class}">

   <xsl:apply-templates/>
  </tr>
 </xsl:template>

 <xsl:template match="n1:th">
  <th class="content">
   <xsl:apply-templates/>
  </th>
 </xsl:template>

 <xsl:template match="n1:td">
  <td class="content">
   <xsl:apply-templates/>
  </td>
 </xsl:template>

 <xsl:template match="n1:table/n1:caption">
  <span style="font-weight:bold; ">
   <xsl:apply-templates/>
  </span>
 </xsl:template>



 <!--   paragraph  -->
 <xsl:template match="n1:paragraph">
  <xsl:apply-templates/>
  <br/>
 </xsl:template>

 <!--     Content w/ deleted text is hidden -->
 <xsl:template match="n1:content[@revised='delete']"/>

 <!--   content  -->
 <xsl:template match="n1:content">
  <xsl:apply-templates/>
 </xsl:template>

 <!--   list  -->
 <xsl:template match="n1:list">
  <xsl:if test="n1:caption">
   <span style="font-weight:bold; ">
    <xsl:apply-templates select="n1:caption"/>
   </span>
  </xsl:if>
  <ul>
   <xsl:for-each select="n1:item">
    <li>
     <xsl:apply-templates/>
    </li>
   </xsl:for-each>
  </ul>
 </xsl:template>

 <xsl:template match="n1:list[@listType='ordered']">
  <xsl:if test="n1:caption">
   <span style="font-weight:bold; ">
    <xsl:apply-templates select="n1:caption"/>
   </span>
  </xsl:if>
  <ol>
   <xsl:for-each select="n1:item">
    <li>
     <xsl:apply-templates/>
    </li>
   </xsl:for-each>
  </ol>
 </xsl:template>

 <!--   caption  -->
 <xsl:template match="n1:caption">
  <xsl:apply-templates/>
  <xsl:text>: </xsl:text>
 </xsl:template>



 <!--   RenderMultiMedia 

this currently only handles GIF's and JPEG's.  It could, however,
be extended by including other image MIME types in the predicate
and/or by generating <object> or <applet> tag with the correct
params depending on the media type  @ID  =$imageRef     referencedObject
-->
 <xsl:template match="n1:renderMultiMedia">
  <xsl:variable name="imageRef" select="@referencedObject"/>
  <xsl:choose>
   <xsl:when test="//n1:regionOfInterest[@ID=$imageRef]">
    <!-- Here is where the Region of Interest image referencing goes -->
    <xsl:if
     test="//n1:regionOfInterest[@ID=$imageRef]//n1:observationMedia/n1:value[@mediaType=&quot;image/gif&quot; or @mediaType=&quot;image/jpeg&quot;]">
     <br clear="all"/>
     <xsl:element name="img">
      <xsl:attribute name="src">graphics/ <xsl:value-of
        select="//n1:regionOfInterest[@ID=$imageRef]//n1:observationMedia/n1:value/n1:reference/@value"
       />
      </xsl:attribute>
     </xsl:element>
    </xsl:if>
   </xsl:when>
   <xsl:otherwise>
    <!-- Here is where the direct MultiMedia image referencing goes -->
    <xsl:if
     test="//n1:observationMedia[@ID=$imageRef]/n1:value[@mediaType=&quot;image/gif&quot; or @mediaType=&quot;image/jpeg&quot;]">
     <br clear="all"/>
     <xsl:element name="img">
      <xsl:attribute name="src">graphics/ <xsl:value-of
        select="//n1:observationMedia[@ID=$imageRef]/n1:value/n1:reference/@value"
       />
      </xsl:attribute>
     </xsl:element>
    </xsl:if>
   </xsl:otherwise>
  </xsl:choose>
 </xsl:template>

 <!--     Stylecode processing   
Supports Bold, Underline and Italics display

-->

 <xsl:template match="//n1:*[@styleCode]">
  <xsl:if test="@styleCode='Bold'">
   <xsl:element name="b">
    <xsl:apply-templates/>
   </xsl:element>
  </xsl:if>

  <xsl:if test="@styleCode='Italics'">
   <xsl:element name="i">
    <xsl:apply-templates/>
   </xsl:element>
  </xsl:if>

  <xsl:if test="@styleCode='Underline'">
   <xsl:element name="u">
    <xsl:apply-templates/>
   </xsl:element>
  </xsl:if>

  <xsl:if
   test="contains(@styleCode,'Bold') and contains(@styleCode,'Italics') and not (contains(@styleCode, 'Underline'))">
   <xsl:element name="b">
    <xsl:element name="i">
     <xsl:apply-templates/>
    </xsl:element>
   </xsl:element>
  </xsl:if>

  <xsl:if
   test="contains(@styleCode,'Bold') and contains(@styleCode,'Underline') and not (contains(@styleCode, 'Italics'))">
   <xsl:element name="b">
    <xsl:element name="u">
     <xsl:apply-templates/>
    </xsl:element>
   </xsl:element>
  </xsl:if>

  <xsl:if
   test="contains(@styleCode,'Italics') and contains(@styleCode,'Underline') and not (contains(@styleCode, 'Bold'))">
   <xsl:element name="i">
    <xsl:element name="u">
     <xsl:apply-templates/>
    </xsl:element>
   </xsl:element>
  </xsl:if>

  <xsl:if
   test="contains(@styleCode,'Italics') and contains(@styleCode,'Underline') and contains(@styleCode, 'Bold')">
   <xsl:element name="b">
    <xsl:element name="i">
     <xsl:element name="u">
      <xsl:apply-templates/>
     </xsl:element>
    </xsl:element>
   </xsl:element>
  </xsl:if>

 </xsl:template>

 <!--     Superscript or Subscript   -->
 <xsl:template match="n1:sup">
  <xsl:element name="sup">
   <xsl:apply-templates/>
  </xsl:element>
 </xsl:template>

 <xsl:template match="n1:sub">
  <xsl:element name="sub">
   <xsl:apply-templates/>
  </xsl:element>
 </xsl:template>

 <!--  Bottomline  -->
 <xsl:template name="bottomline">
  <br/>
  <br/>
  <b>
   <xsl:text>Signed by: </xsl:text>
  </b>
  <br/>
  <xsl:apply-templates
   select="/n1:ClinicalDocument/n1:legalAuthenticator/n1:assignedEntity/n1:assignedPerson/n1:name"/>
  <xsl:text> on </xsl:text>
  <xsl:apply-templates mode="Date"
   select="//n1:ClinicalDocument/n1:legalAuthenticator/n1:time"/>
 </xsl:template>


 <!-- END Component text rendering -->
</xsl:stylesheet>
