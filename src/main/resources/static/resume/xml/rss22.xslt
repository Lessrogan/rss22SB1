<?xml version="1.0"?>

<xsl:stylesheet version="3.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:rss="http://univrouen.fr/rss22"
xmlns:fn="http://www.w3.org/2005/xpath-functions">

<xsl:template match="/rss:feed">
  <xsl:element name="html">
  
  	<xsl:element name="link">
  		<xsl:attribute name="href">/resume/css/rss22.css</xsl:attribute>
  		<xsl:attribute name="rel">stylesheet</xsl:attribute>
	</xsl:element>
  	<xsl:element name="body">
    
	  	<xsl:element name="h1"><xsl:text>TP4 - Flux RSS22</xsl:text></xsl:element>
<!-- 	  	<xsl:element name="p"><xsl:text>Le </xsl:text><xsl:value-of select="fn:format-date(fn:current-date(),'[D01]/[M01]/[Y01]')"/></xsl:element> -->	   	
	   	
	  	<xsl:element name="h2">Sommaire</xsl:element>
	  	<xsl:call-template name="item_summary_template"/>
	   	
  	</xsl:element>
  </xsl:element>
</xsl:template>

<xsl:template name="item_summary_template">

   <xsl:variable name="item_count">
      <xsl:value-of select="count(rss:item)"/>
   </xsl:variable>

	<xsl:element name="table">
		<xsl:element name="tr">
			<xsl:element name="th">GUID</xsl:element>
			<xsl:element name="th">Titre</xsl:element>
			<xsl:element name="th">Date</xsl:element>
		</xsl:element>
	    <xsl:for-each select="rss:item">
			<xsl:element name="tr">
				<xsl:element name="td">
	  				<xsl:value-of select="rss:guid"/>
		     	</xsl:element>
				<xsl:element name="td">
	  				<xsl:value-of select="rss:title"/>
		     	</xsl:element>
				<xsl:element name="td">
	  				<xsl:value-of select="rss:published"/>
		     	</xsl:element>
	     	</xsl:element>
	    </xsl:for-each>
     </xsl:element>
</xsl:template>

</xsl:stylesheet> 