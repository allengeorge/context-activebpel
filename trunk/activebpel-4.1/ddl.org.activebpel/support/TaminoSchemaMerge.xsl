<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
   xmlns:xalan="http://xml.apache.org/xslt" xmlns:tsd="http://namespaces.softwareag.com/tamino/TaminoSchemaDefinition"
         xmlns:xs="http://www.w3.org/2001/XMLSchema">

   <xsl:output method="xml" encoding="UTF-8" indent="yes" xalan:indent-amount="3" />

   <xsl:param name="schemaBFile" />
   
   <xsl:variable name="schemaB" select="document($schemaBFile)" />

   <xsl:template match="/xs:schema">
      <xs:schema xmlns:tsd="http://namespaces.softwareag.com/tamino/TaminoSchemaDefinition"
         xmlns:xs="http://www.w3.org/2001/XMLSchema">
         <xs:annotation>
            <xs:appinfo>
               <tsd:schemaInfo>
                  <xsl:attribute name="name">
                     <xsl:value-of select="xs:annotation/xs:appinfo/tsd:schemaInfo/@name"/>
                  </xsl:attribute>
                  <xsl:copy-of select="//tsd:schemaInfo/tsd:collection" />
                  <xsl:copy-of select="//tsd:schemaInfo/tsd:doctype" />
                  <xsl:copy-of select="$schemaB//tsd:schemaInfo/tsd:doctype" />
               </tsd:schemaInfo>
            </xs:appinfo>
         </xs:annotation>
         <xsl:copy-of select="/xs:schema/xs:complexType" />
         <xsl:copy-of select="$schemaB/xs:schema/xs:complexType" />
         <xsl:copy-of select="/xs:schema/xs:element" />
         <xsl:copy-of select="$schemaB/xs:schema/xs:element" />
      </xs:schema>
   </xsl:template>

</xsl:stylesheet>
