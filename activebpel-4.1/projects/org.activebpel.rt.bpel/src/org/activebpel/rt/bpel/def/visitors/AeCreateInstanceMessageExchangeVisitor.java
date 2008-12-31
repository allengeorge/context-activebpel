//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeCreateInstanceMessageExchangeVisitor.java,v 1.1 2006/11/03 22:48:0
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
package org.activebpel.rt.bpel.def.visitors;

import java.util.Stack;

import org.activebpel.rt.bpel.def.AePartnerLinkOpKey;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.activity.IAeReceiveActivityDef;

/**
 * Records the messageExchange attribute for all of the createInstance activities.
 * This value is used to populate the messageExchange value on the reply object
 * that we queue with the create message. This is required to select the correct
 * reply receiver when the reply activity executes.
 * 
 */
public class AeCreateInstanceMessageExchangeVisitor extends AeAbstractEntryPointVisitor
{

   /** Stack of our enclosed scopes - used to quickly reference scopes that might declare message exchange values. */
   private Stack mEnclosedScopes = new Stack();
   
   /**
    * Default ctor.
    *
    */
   public AeCreateInstanceMessageExchangeVisitor()
   {
      super();
   }
      
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityScopeDef)
    */
   public void visit(AeActivityScopeDef aDef)
   {
      mEnclosedScopes.push(aDef);
      super.visit(aDef);
      mEnclosedScopes.pop();
   }   

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractEntryPointVisitor#processEntryPoint(org.activebpel.rt.bpel.def.activity.IAeReceiveActivityDef)
    */
   protected void processEntryPoint(IAeReceiveActivityDef aDef)
   {
      AePartnerLinkOpKey key = aDef.getPartnerLinkOperationKey();
      getProcessDef().addCreateInstanceMessageExchange(key, formatMessageExchange(aDef.getMessageExchange()));
   }

   /**
    * Formats the messageExchange value by prepending the location path of the
    * declaring scope.
    * @param aMessageExchange
    */
   protected String formatMessageExchange(String aMessageExchange)
   {
      String path = getPathForDeclaringScope(aMessageExchange);
      
      return path + "/" + aMessageExchange; //$NON-NLS-1$
   }
   
   /**
    * Gets the path for the declaring scope by walking the enclosed scopes stack
    * and stopping when it finds one that declares the messageExchange value.
    * @param aMessageExchange
    */
   protected String getPathForDeclaringScope(String aMessageExchange)
   {
      // Note: It's ok to use the def paths here because the create instance
      //       activities that we're looking for will never be nested within
      //       a parallel forEach -- which is the activity that necessitated
      //       moving to getting the paths from the impl objects instead of the 
      //       defs.
      for(int i=mEnclosedScopes.size()-1; i>=0; i--)
      {
         AeActivityScopeDef enclosedScope = (AeActivityScopeDef) mEnclosedScopes.get(i);
         if (enclosedScope.getScopeDef().declaresMessageExchange(aMessageExchange))
         {
            return enclosedScope.getLocationPath();
         }
      }
      
      if (getProcessDef().declaresMessageExchange(aMessageExchange))
         return getProcessDef().getLocationPath();
      
      // if we get here then the bpel isn't valid because it contains a receive
      // or an onMessage that has a messageExchange value that is not declared
      // within an enclosing scope. I'll return null here in favor of reporting
      // the error in the validation visitor.
      return null;
   }

}
