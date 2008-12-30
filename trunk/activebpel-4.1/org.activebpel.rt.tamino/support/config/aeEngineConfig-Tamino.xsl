<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
   xmlns:xalan="http://xml.apache.org/xslt">
   
   <xsl:output method="xml" encoding="UTF-8" indent="yes" xalan:indent-amount="3"/>

   <!-- Default entry template - simply outputs the entry as-is. -->
   <xsl:template match="entry">
      <entry>
         <xsl:attribute name="name">
            <xsl:value-of select="@name"/>
         </xsl:attribute>
         <xsl:choose>
            <xsl:when test="@value">
               <xsl:attribute name="value">
                  <xsl:value-of select="@value"/>
               </xsl:attribute>
            </xsl:when>
            <xsl:otherwise>
               <xsl:apply-templates select="* | comment() | text()"/>
            </xsl:otherwise>
         </xsl:choose>
      </entry>
   </xsl:template>

   <!-- Preserve comments (unlikely to work, but can't hurt). -->
   <xsl:template match="comment()">
      <xsl:comment><xsl:value-of select="."/></xsl:comment>
   </xsl:template>

   <!-- Gets the ball rolling, matches the root node and starts processing. -->
   <xsl:template match="/config">
      <config>
         <xsl:apply-templates select="* | comment() | text()"/>
      </config>
   </xsl:template>

   <!-- Set the Description of the config. -->
   <xsl:template match="/config/entry[@name = 'Description']">
      <entry value="ActiveBPEL Tamino Configuration" name="Description"/>
   </xsl:template>
   
   <!-- Change the Storage Provider. -->
   <xsl:template match="/config/entry[@name = 'PersistentStore']/entry[@name = 'Factory']/entry[@name = 'StorageProviderFactory']">
      <entry name="StorageProviderFactory">
         <entry value="org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoStorageProviderFactory" name="Class"/>
         <entry value="tamino" name="DatabaseType"/>
         <entry value="@DB_VERSION@" name="Version"/>
         <entry name="DataSource">
            <entry value="org.activebpel.rt.bpel.server.engine.storage.tamino.AePooledTaminoDataSource" name="Class"/>
            <entry value="@TAMINO_URL@" name="URL"/>
            <entry value="@TAMINO_DB_NAME@" name="Database"/>
            <entry value="ActiveBPEL_collection" name="Collection"/>
            <entry value="" name="Domain"/>
            <entry value="" name="Username"/>
            <entry value="" name="Password"/>

            <entry value="__aei__pool__" name="PoolName"/>
            <entry value="60" name="MaxConnections"/>
            <entry value="10" name="InitialConnections"/>
            <entry value="60" name="MaxTransactionDuration"/>
            <entry value="60" name="TimeOut"/>
         </entry>
      </entry>
   </xsl:template>

   <!-- Change the Transaction Manager Factory -->
   <xsl:template match="/config/entry[@name = 'TransactionManagerFactory']/entry[@name = 'Class']">
      <entry value="org.activebpel.rt.bpel.server.engine.storage.tamino.tx.AeTaminoTransactionManagerFactory" name="Class" />
   </xsl:template>

</xsl:stylesheet>
