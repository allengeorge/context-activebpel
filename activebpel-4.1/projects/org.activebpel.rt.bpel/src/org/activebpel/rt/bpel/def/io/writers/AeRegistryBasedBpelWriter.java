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
package org.activebpel.rt.bpel.def.io.writers;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.io.IAeBpelWriter;
import org.activebpel.rt.bpel.def.io.registry.IAeBpelRegistry;
import org.activebpel.rt.bpel.def.visitors.AeDefCreateInvokeScopeVisitor;
import org.activebpel.rt.bpel.def.visitors.AeDefInlineInvokeScopeVisitor;
import org.w3c.dom.Document;

/**
 * IAeBpelWriter impl.
 * <br />
 * Delegates the actual work to the <code>AeBpelDefTraverserVisitor</code>
 * class. 
 */
public class AeRegistryBasedBpelWriter implements IAeBpelWriter
{
   /** bpel registry */
   private IAeBpelRegistry mBpelRegistry;
   
   /**
    * ctor accepts registries
    * 
    * @param aBpelRegistry
    * @param aExtensionRegistry
    */
   public AeRegistryBasedBpelWriter(IAeBpelRegistry aBpelRegistry)
   {
      mBpelRegistry = aBpelRegistry;
   }

   /**
    * Uses the <code>AeBpelWriterVisitor</code> to traverse the
    * process def and convert it and all of its children to a 
    * DOM tree. 
    * 
    * @see org.activebpel.rt.bpel.def.io.IAeBpelWriter#writeBPEL(org.activebpel.rt.bpel.def.AeProcessDef, boolean)
    */
   public Document writeBPEL(AeProcessDef aProcessDef, boolean aUsePrefixes) throws AeBusinessProcessException
   {
      try
      {
         preProcessDefForWriting(aProcessDef);

         // If the use-prefixes flag is set, then remove any default namespace declaration
         // that might exist on the process def.  This will cause the serializers to prefix
         // the Elements that get created for the defs (and make sure to save the default 
         // namespace so that it can be restored later).
         String defaultNS = aProcessDef.getDefaultNamespace();
         if (aUsePrefixes)
         {
            aProcessDef.removeDefaultNamespace();
         }
         else
         {
            aProcessDef.setDefaultNamespace(aProcessDef.getNamespace());
         }

         AeBpelDefTraverserVisitor writerVisitor = new AeBpelDefTraverserVisitor(mBpelRegistry);
         try
         {
            writerVisitor.visit(aProcessDef);
         }
         finally
         {
            // Restore the previously saved default namespace, if any.
            aProcessDef.setDefaultNamespace(defaultNS);
         }
         
         return writerVisitor.getProcessDoc();
      }
      catch( AeBpelWriterException e )
      {
         throw new AeBusinessProcessException( e.getMessage(), e.getCause() );
      }
      catch( Throwable t )
      {
         throw new AeBusinessProcessException(t.getMessage(), t);
      }
      finally
      {
         postProcessDefForWriting(aProcessDef);
      }
   }
   
   /**
    * Called before serializing a Def.  This can be used to change the def
    * in some way that is needed only for writing it out to XML.
    * 
    * @param aDef
    */
   private static void preProcessDefForWriting(AeProcessDef aDef)
   {
      AeDefInlineInvokeScopeVisitor invokeScopeVizzy = new AeDefInlineInvokeScopeVisitor();
      invokeScopeVizzy.visit(aDef);
   }

   /**
    * Called after serializing a Def.  This is used to undo any changes to
    * the def made by <code>preProcessDefForWriting</code>.
    * 
    * @param aDef
    */
   private static void postProcessDefForWriting(AeProcessDef aDef)
   {
      AeDefCreateInvokeScopeVisitor invokeScopeVizzy = new AeDefCreateInvokeScopeVisitor();
      invokeScopeVizzy.visit(aDef);
   }
}

