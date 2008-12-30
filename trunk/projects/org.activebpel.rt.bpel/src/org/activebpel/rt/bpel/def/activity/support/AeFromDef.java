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
package org.activebpel.rt.bpel.def.activity.support;

import java.util.List;

import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Node;

/**
 * Models the <code>from</code> element in a copy operation or variable initialization. Broke this out as
 * a separate class so it can be explicitly visited.
 */
public class AeFromDef extends AeVarDef
{
   /** The value of the opaque attribute (if any). */
   private boolean mOpaque;
   /** The value of the literal (if any). */
   private AeLiteralDef mLiteralDef;
   /** The value of the endpoint reference (if any). */
   private String mEndpointReference;

   /**
    * Default ctor
    */
   public AeFromDef()
   {
      super();
   }

   /**
    * Returns the literal Node for the copy variable.
    * @return Node the literal node
    */
   public Node getLiteral()
   {
      if (getLiteralDef() != null)
      {
         List nodes = getLiteralDef().getChildNodes();
         if (nodes.size() > 0)
         {
            return (Node) nodes.get(0);
         }
      }

      return null;
   }

   /**
    * Accessor method to obtain the opaque flag.
    * 
    * @return flag indicating if assignment variable is opaque
    */
   public boolean isOpaque()
   {
      return mOpaque;
   }

   /**
    * Mutator method to set the opaque flag.
    * 
    * @param aOpaque boolean flag indicating if assignment variable is opaque
    */
   public void setOpaque(boolean aOpaque)
   {
      mOpaque = aOpaque;
   }
   

   /**
    * Accessor method to obtain the endpoint reference for the object.
    * 
    * @return name of the endpoint reference for the object
    */
   public String getEndpointReference()
   {
      return mEndpointReference;
   }

   /**
    * Mutator method to set the endpoint reference for the object.
    * 
    * @param aEndpointReference the endpoint reference value to be set
    */
   public void setEndpointReference(String aEndpointReference)
   {
      mEndpointReference = aEndpointReference;
   }

   /**
    * Returns true if this from is empty.  This is used during validation, since the from portion
    * of an assign's copy child should never be empty.
    */
   public boolean isEmpty()
   {
      return AeUtil.isNullOrEmpty(getExpression()) && getLiteral() == null && !isOpaque()
            && AeUtil.isNullOrEmpty(getPartnerLink()) && AeUtil.isNullOrEmpty(getVariable());
   }

   /**
    * @return Returns the literalDef.
    */
   public AeLiteralDef getLiteralDef()
   {
      return mLiteralDef;
   }

   /**
    * @param aLiteralDef The literalDef to set.
    */
   public void setLiteralDef(AeLiteralDef aLiteralDef)
   {
      mLiteralDef = aLiteralDef;
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeExpressionDef#getBpelNamespace()
    */
   public String getBpelNamespace()
   {
      return AeDefUtil.getProcessDef(this).getNamespace();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
   
   /**
    * Returns true if the from is reading partner link data from the myRole
    */
   public boolean isMyRole()
   {
      // TODO (MF) use a type for this instead of the string
      return "myRole".equals(getEndpointReference()); //$NON-NLS-1$
   }
   
   /**
    * Returns true if the from is reading partner link data from the partnerRole
    */
   public boolean isPartnerRole()
   {
      return "partnerRole".equals(getEndpointReference()); //$NON-NLS-1$
   }
   
   /**
    * Returns true if this from is a literal from.
    */
   public boolean isLiteral()
   {
      return getLiteralDef() != null;
   }
}
