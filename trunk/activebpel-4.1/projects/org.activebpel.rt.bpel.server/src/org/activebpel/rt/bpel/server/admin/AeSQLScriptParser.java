// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/AeSQLScriptParser.java,v 1.2 2005/02/01 19:56:3
/*
 * Copyright (c) 2004-2006 Active Endpoints, Inc.
 *
 * This program is licensed under the terms of the GNU General Public License
 * Version 2 (the "License") as published by the Free Software Foundation, and 
 * the ActiveBPEL Licensing Policies (the "Policies").  A copy of the License 
 * and the Policies were distributed with this program.  
 *
 * The License is available at:
 * http: *www.gnu.org/copyleft/gpl.html
 *
 * The Policies are available at:
 * http: *www.activebpel.org/licensing/index.html
 *
 * Unless required by applicable law or agreed to in writing, this program is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied.  See the License and the Policies
 * for specific language governing the use of this program.
 */
package org.activebpel.rt.bpel.server.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.activebpel.rt.util.AeUtil;

/**
 * Utility to read in sql script files and return (string) sql statements.
 * NOTE: Sql statements and comments can span multiple lines, but they
 * should not be intertwined.  Multiple sql statements should not appear
 * on the same line.
 */
public class AeSQLScriptParser
{
   /** Reader for the sql file source. */
   protected BufferedReader mReader;
   /** Sql statement string. */
   String mNextStatement;
   
   /**
    * Constructor.
    * @param aReader Reader for the sql file source.  Callers 
    * are responsible for closing this reader after the next() call
    * returns false. 
    */
   public AeSQLScriptParser( Reader aReader )
   {
      mReader = new BufferedReader(aReader);
   }
   
   /**
    * Moves the reader to the next statement.
    * @return Returns true if there is a current statement.
    * @throws IOException
    */
   public boolean next() throws IOException
   {
      StringBuffer buffer = new StringBuffer();
      String line = null;
      String sep = ""; //$NON-NLS-1$
      boolean isLineWithSemiColon = false;
      
      while( !isLineWithSemiColon && (line=getReader().readLine()) != null )
      {
         line = line.trim();
         if( !isCommentOrEmptyLine( line ) )
         {
            buffer.append( sep );
            buffer.append( line );
            sep = " "; //$NON-NLS-1$
            isLineWithSemiColon = line.endsWith(";");       //$NON-NLS-1$
         }
      }
      
      setNextStatement( buffer.toString() );
      return !AeUtil.isNullOrEmpty( getNextStatement() );
   }
   
   /**
    * Returns true if the line is a comment or empty.
    * @param aLine
    */
   protected boolean isCommentOrEmptyLine( String aLine )
   {
      return AeUtil.isNullOrEmpty(aLine) || aLine.startsWith("#"); //$NON-NLS-1$
   }
   
   /**
    * Getter for the sql statement.
    */
   public String getNextStatement()
   {
      return mNextStatement;
   }
   
   /**
    * Setter for the sql statement.
    * @param aStatement
    */
   protected void setNextStatement( String aStatement )
   {
      mNextStatement = aStatement;
   }
   
   /**
    * Getter for the reader.
    */
   protected BufferedReader getReader()
   {
      return mReader;
   }
}
