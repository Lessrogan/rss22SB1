<?xml version="1.0"?>

<xsl:stylesheet version="3.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:rss="http://univrouen.fr/rss22"
xmlns:fn="http://www.w3.org/2005/xpath-functions">

<xsl:template match="/rss:feed">
  <xsl:element name="html">
  
  	<xsl:element name="link">
  		<xsl:attribute name="href">xml/rss22.css</xsl:attribute>
  		<xsl:attribute name="rel">stylesheet</xsl:attribute>
	</xsl:element>
  	<xsl:element name="body">
    
	  	<xsl:element name="h1"><xsl:text>TP4 - Flux RSS22</xsl:text></xsl:element>
<!-- 	  	<xsl:element name="p"><xsl:text>Le </xsl:text><xsl:value-of select="fn:format-date(fn:current-date(),'[D01]/[M01]/[Y01]')"/></xsl:element>
 -->	   	
	   	
	  	<xsl:element name="h2">Sommaire</xsl:element>
	  	<xsl:call-template name="item_summary_template"/>
	   	
	  	<xsl:element name="h2">Détails des informations</xsl:element>
	  	
	  	<xsl:apply-templates select="rss:item"></xsl:apply-templates>
	   	
  	</xsl:element>
  </xsl:element>
</xsl:template>

<xsl:template name="item_summary_template">

   <xsl:variable name="item_count">
      <xsl:value-of select="count(rss:item)"/>
   </xsl:variable>

	<xsl:element name="table">
		<xsl:element name="tr">
			<xsl:element name="th">n°</xsl:element>
			<xsl:element name="th">Titre</xsl:element>
			<xsl:element name="th">Date</xsl:element>
			<xsl:element name="th">Catégorie</xsl:element>
			<xsl:element name="th">Auteur</xsl:element>
		</xsl:element>
	    <xsl:for-each select="rss:item">
			<xsl:element name="tr">
				<xsl:element name="td">
	  				<xsl:value-of select="position()"/>
					<xsl:text>/</xsl:text>
	  				<xsl:value-of select="$item_count"/>
		     	</xsl:element>
				<xsl:element name="td">
					<xsl:choose>
						<xsl:when test="string-length(rss:title) &gt; 45">
			     			<xsl:value-of select="substring(rss:title,0,45)"/>
			     			<xsl:text>...</xsl:text>
			     		</xsl:when>
			     		<xsl:otherwise>
			     			<xsl:value-of select="rss:title"/>
			     		</xsl:otherwise>
		     		</xsl:choose>
		     	</xsl:element>
	     	</xsl:element>
	    </xsl:for-each>
     </xsl:element>
</xsl:template>

<xsl:template name="item_template" match="rss:item">
	<xsl:element name="h3"><xsl:value-of select="rss:title"/></xsl:element>
	<xsl:element name="p">
		<xsl:text>(guid=</xsl:text>
		<xsl:element name="span">
			<xsl:attribute name="class">guid_title</xsl:attribute>
			<xsl:value-of select="rss:guid"/>
		</xsl:element>
		<xsl:text>)</xsl:text>
	</xsl:element>
	<xsl:element name="img">
		<xsl:attribute name="src">
			<xsl:value-of select="rss:image/@href"/>
			</xsl:attribute>
	</xsl:element>
	<xsl:element name="p">
		<xsl:text>Catégorie : </xsl:text>
		<xsl:value-of select="rss:category/@term"/>
	</xsl:element>
	<xsl:element name="p">
		<xsl:text>Publié le </xsl:text>
		<xsl:value-of select="rss:published"/>
	</xsl:element>
	<xsl:element name="p"></xsl:element>
	<xsl:element name="p">
		<xsl:value-of select="rss:content"/>
	</xsl:element>
</xsl:template>

</xsl:stylesheet> 