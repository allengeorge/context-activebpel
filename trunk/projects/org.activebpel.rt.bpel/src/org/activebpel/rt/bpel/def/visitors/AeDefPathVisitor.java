// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefPathVisitor.java,v 1.20 2006/11/14 19:47:3
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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeNamedDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.util.AeUtil;

/**
 * Visitor responsible for creating location paths for process definition objects.
 * 
 * This visitor relies on the base class to do the basic visiting and traversing. The {@link #traverse(AeBaseDef)}
 * method is overridden here in order to determine what the location path should be for the given
 * def. This location path is determined by using an instance of {@link IAeDefPathSegmentVisitor}. This level
 * of indirection is necessary since the segment paths may differ by bpel version (i.e. switch/if).
 */
public class AeDefPathVisitor extends AeAbstractDefVisitor implements IAeDefPathVisitor
{
   /** Current path */
   protected String mPath=""; //$NON-NLS-1$
   /** Path Maps */
   protected HashSet mPaths = new HashSet();
   /** Next available location id */
   private int mNextLocationId = 1;
   /** Maps location paths to location ids */
   protected final Map mLocationPathMap = new HashMap();
   /** visitor to build a single segment of the path for a def */
   private IAeDefPathSegmentVisitor mSegmentVisitor;

   /**
    * Ctor
    * @param aPathSegmentVisitor - provides the value for the def's segment of the location path
    */
   public AeDefPathVisitor(IAeDefPathSegmentVisitor aPathSegmentVisitor)
   {
      this(aPathSegmentVisitor, 1);
   }
   
   /**
    * Ctor
    * @param aPathSegmentVisitor
    * @param aNextLocationId
    */
   public AeDefPathVisitor(IAeDefPathSegmentVisitor aPathSegmentVisitor, int aNextLocationId)
   {
      setNextLocationId( aNextLocationId );
      setTraversalVisitor(new AeTraversalVisitor(new AeDefTraverser(), this));
      setSegmentVisitor(aPathSegmentVisitor);
   }
   
   /**
    * All of the base classes's visit methods call this traverse method. This gives us
    * a single place to determine the segment for the current def and then create
    * a unique location path for the def using this segment and its parent's location path.
    * 
    * If this def doesn't have a segment, then we'll traverse it and continue visiting
    * its children.
    * 
    * If this def is a named def, then we'll create a path using its name in addition to its
    * parent's path and its segment path and then traverse it.
    * 
    * If this def is not a named def, then we'll create a path using only its parent's path
    * and its segment path and then traverse it.
    * 
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#traverse(org.activebpel.rt.bpel.def.AeBaseDef)
    */
   protected void traverse(AeBaseDef aDef)
   {
      // clear any previously set path segment prior to visiting
      getSegmentVisitor().setPathSegment(null);
      aDef.accept(getSegmentVisitor());
      String segment = getSegmentVisitor().getPathSegment();
      
      // if we got a segment, then create a path and traverse
      if (segment != null)
      {
         if (aDef instanceof AeNamedDef)
         {
            createPathAndTraverse((AeNamedDef) aDef, segment);
         }
         else
         {
            createPathAndTraverse(aDef, segment, null);
         }
      }
      else
      {
         // there is no path to set on this def. Use the traversal visitor to move onto the next path
         aDef.accept(getTraversalVisitor());
      }
   }
   
   /**
    * Appends the def's name to the xpath to make a more human readable xpath.
    * @param aDef
    * @param aAppendPath
    */
   protected void createPathAndTraverse(AeNamedDef aDef, String aAppendPath)
   {
      String name = aDef.getName();
      if (!AeUtil.isNullOrEmpty(name))
      {
         StringBuffer buffer = new StringBuffer(aAppendPath);
         buffer.append("[@name='"); //$NON-NLS-1$
         buffer.append(name);
         buffer.append("']");  //$NON-NLS-1$
         createPathAndTraverse((AeBaseDef)aDef, aAppendPath, buffer.toString());
      }
      else
      {
         createPathAndTraverse((AeBaseDef)aDef, aAppendPath, null);
      }
   }

   /**
    * Accepts the extra arg of a name path which will be non null for AeNamedDef
    * instances that had a non-null value for their name. This allows for more
    * user friendly xpath names while not running into problem of generating
    * inaccurate xpath.
    * @param aDef - the definition object we're visiting
    * @param aAppendPath - the path to the def object
    * @param aNamePath - the path with the name attribute in it
    */
   private void createPathAndTraverse(AeBaseDef aDef, String aAppendPath, String aNamePath)
   {
      if(aAppendPath != null)
      {
         String savePath = mPath;
         if (AeUtil.isNullOrEmpty(aNamePath))
         {
            mPath = createUniquePath(aDef, aAppendPath);
         }
         else
         {
            // calling createUniquePath TWICE here so we'll have a record of the
            // path in both its named and unnamed form. This addresses the issue
            // of having multiple named objects as child elements and only having
            // SOME of them contain names.
            // i.e.
            // <scope name="one"/>
            // <scope/>
            // <scope name="three"/>
            // without this extra call, the path for the second scope will be
            // "/scope" since there was no other scope recorded with that path.
            // It SHOULD be /scope[2].
            createUniquePath(aDef, aAppendPath);
            mPath = createUniquePath(aDef, aNamePath);
         }
         updateLocationId(aDef);
         aDef.accept(getTraversalVisitor());
         mPath = savePath;
      }
      else
      {
         aDef.accept(getTraversalVisitor());
      }
   }

   /**
    * Generates a new location id for the current path and puts this value into a map.
    * @param aDef
    */
   protected void updateLocationId(AeBaseDef aDef)
   {
      int locationId = getNextLocationId();
      setNextLocationId(locationId + 1);
      mLocationPathMap.put(mPath, new Integer(locationId));
      recordLocationPathAndId(aDef, mPath, locationId);
   }   
   
   /**
    * Sets the location path and id on the def object.
    * @param aDef
    * @param aLocationPath
    * @param aLocationId
    */
   protected void recordLocationPathAndId(AeBaseDef aDef, String aLocationPath, int aLocationId)
   {
      aDef.setLocationPath(aLocationPath);
      aDef.setLocationId(aLocationId);
   }

   /**
    * Checks map of paths created by vistors and appends indexes to make unique
    * @param aDef The def object we're creating the path for
    * @param aAppendPath path to append to main path
    * @return the unique path
    */
   protected String createUniquePath(AeBaseDef aDef, String aAppendPath)
   {
      StringBuffer testPath = new StringBuffer(mPath);
      testPath.append("/"); //$NON-NLS-1$
      testPath.append(aAppendPath);
      int initLen = testPath.length();

      // test path for uniqueness in set and loop until unique
      for(int i = 2; mPaths.contains(testPath.toString()); ++i)
      {
         testPath.setLength(initLen);
         testPath.append("[").append(i).append("]"); //$NON-NLS-1$ //$NON-NLS-2$
      }

      // good path found so save the path in set and return the good path
      String goodPath = testPath.toString();
      mPaths.add(goodPath);
      return goodPath;
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      // Do the cast here to AeBaseDef so we won't have the name predicate included
      // in the path. There is no need to have the name in place for the process
      // element since there is only one per document.
      createPathAndTraverse((AeBaseDef)aDef, IAeBPELConstants.TAG_PROCESS, null);
   }

   /**
    * Returns next available location id.
    */
   public int getNextLocationId()
   {
      return mNextLocationId;
   }

   /**
    * Setter for the location id
    *
    * @param aId
    */
   protected void setNextLocationId(int aId)
   {
      mNextLocationId = aId;
   }

   /**
    * Returns set of generated location paths.
    */
   public Set getLocationPaths()
   {
      return Collections.unmodifiableSet(mLocationPathMap.keySet());
   }

   /**
    * Returns location id corresponding to a location path.
    */
   public int getLocationId(String aLocationPath)
   {
      Integer id = (Integer) mLocationPathMap.get(aLocationPath);
      return id.intValue();
   }

   /**
    * Getter for the segment visitor
    */
   public IAeDefPathSegmentVisitor getSegmentVisitor()
   {
      return mSegmentVisitor;
   }
   
   /**
    * Setter for the segment visitor
    * @param aVisitor
    */
   public void setSegmentVisitor(IAeDefPathSegmentVisitor aVisitor)
   {
      mSegmentVisitor = aVisitor;
   }
   
   /**
    * @return Returns the path.
    */
   protected String getPath()
   {
      return mPath;
   }

   /**
    * @param aPath The path to set.
    */
   protected void setPath(String aPath)
   {
      mPath = aPath;
   }
}
