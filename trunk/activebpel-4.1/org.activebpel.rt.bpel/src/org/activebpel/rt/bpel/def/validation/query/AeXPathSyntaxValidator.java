// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/query/AeXPathSyntaxValidator.java,v 1.1 2005/06/08 12:50:3
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
package org.activebpel.rt.bpel.def.validation.query;

import org.activebpel.rt.AeException;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.saxpath.XPathHandler;
import org.jaxen.saxpath.XPathReader;
import org.jaxen.saxpath.helpers.XPathReaderFactory;

/**
 * This class validates xpath expression syntax only.
 */
public class AeXPathSyntaxValidator implements XPathHandler
{
   /** True if we found a construct we can not validate. */
   protected boolean mStopValidate = false;
   
   /**
    * Constructor for the validator.
    */
   public AeXPathSyntaxValidator()
   {
   }

   /** 
    * Call to perform validation.  This is the main entry point for validation
    * and can be called multiple times by a process to validate its queries.
    * However this entry point is not reentrant and should only be called again 
    * after it returns. 
    * 
    * @param aExpr
    * @throws AeException
    */
   public void validate(String aExpr) throws AeException
   {
      try
      {
         // reset instance data 
         mStopValidate = false;

         // parse the passed xpath and validate through our handler implementation
         XPathReader reader = XPathReaderFactory.createReader();
         reader.setXPathHandler( this );
         reader.parse( aExpr );
      }
      catch (Exception e)
      {
         throw new AeException(e.getMessage(), e);
      }
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startXPath()
    */
   public void startXPath() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endXPath()
    */
   public void endXPath() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startPathExpr()
    */
   public void startPathExpr() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endPathExpr()
    */
   public void endPathExpr() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startAbsoluteLocationPath()
    */
   public void startAbsoluteLocationPath() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endAbsoluteLocationPath()
    */
   public void endAbsoluteLocationPath() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startRelativeLocationPath()
    */
   public void startRelativeLocationPath() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endRelativeLocationPath()
    */
   public void endRelativeLocationPath() throws SAXPathException
   {
   }

   /**
    * Checks the name passed for applicability at the current level.
    * @see org.jaxen.saxpath.XPathHandler#startNameStep(int, java.lang.String, java.lang.String)
    */
   public void startNameStep(int axis, String prefix, String localName) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endNameStep()
    */
   public void endNameStep() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startTextNodeStep(int)
    */
   public void startTextNodeStep(int axis) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endTextNodeStep()
    */
   public void endTextNodeStep() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startCommentNodeStep(int)
    */
   public void startCommentNodeStep(int axis) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endCommentNodeStep()
    */
   public void endCommentNodeStep() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startAllNodeStep(int)
    */
   public void startAllNodeStep(int axis) throws SAXPathException
   {
      // @todo we may be able to change the namestep check to take this into account 
      mStopValidate = true;
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endAllNodeStep()
    */
   public void endAllNodeStep() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startProcessingInstructionNodeStep(int, java.lang.String)
    */
   public void startProcessingInstructionNodeStep(int axis, String name) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endProcessingInstructionNodeStep()
    */
   public void endProcessingInstructionNodeStep() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startPredicate()
    */
   public void startPredicate() throws SAXPathException
   {
      mStopValidate = true;
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endPredicate()
    */
   public void endPredicate() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startFilterExpr()
    */
   public void startFilterExpr() throws SAXPathException
   {
      mStopValidate = true;
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endFilterExpr()
    */
   public void endFilterExpr() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startOrExpr()
    */
   public void startOrExpr() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endOrExpr(boolean)
    */
   public void endOrExpr(boolean create) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startAndExpr()
    */
   public void startAndExpr() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endAndExpr(boolean)
    */
   public void endAndExpr(boolean create) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startEqualityExpr()
    */
   public void startEqualityExpr() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endEqualityExpr(int)
    */
   public void endEqualityExpr(int equalityOperator) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startRelationalExpr()
    */
   public void startRelationalExpr() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endRelationalExpr(int)
    */
   public void endRelationalExpr(int relationalOperator) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startAdditiveExpr()
    */
   public void startAdditiveExpr() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endAdditiveExpr(int)
    */
   public void endAdditiveExpr(int additiveOperator) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startMultiplicativeExpr()
    */
   public void startMultiplicativeExpr() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endMultiplicativeExpr(int)
    */
   public void endMultiplicativeExpr(int multiplicativeOperator) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startUnaryExpr()
    */
   public void startUnaryExpr() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endUnaryExpr(int)
    */
   public void endUnaryExpr(int unaryOperator) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startUnionExpr()
    */
   public void startUnionExpr() throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endUnionExpr(boolean)
    */
   public void endUnionExpr(boolean create) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#number(int)
    */
   public void number(int number) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#number(double)
    */
   public void number(double number) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#literal(java.lang.String)
    */
   public void literal(String literal) throws SAXPathException
   {
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#variableReference(java.lang.String, java.lang.String)
    */
   public void variableReference(String prefix, String variableName) throws SAXPathException
   {
      mStopValidate = true;
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#startFunction(java.lang.String, java.lang.String)
    */
   public void startFunction(String prefix, String functionName) throws SAXPathException
   {
      // TODO handle embedded getVariableData calls and other functions
      // mStopValidate=false ;
      mStopValidate = true;
   }

   /**
    * @see org.jaxen.saxpath.XPathHandler#endFunction()
    */
   public void endFunction() throws SAXPathException
   {
   }
}
