// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/graph/AeGraphImageParameters.java,v 1.2 2007/04/11 17:54:5
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
package org.activebpel.rt.bpeladmin.war.web.graph;

/**
 * Struct to hold graph image parameters.
 */
public class AeGraphImageParameters
{
   // These have to be declared public for .NET.
   public long mProcessId;
   public int mDeploymentProcessId;
   public int mPlanId;
   public int mPart;
   public String mPivotPath;
   public int mTileWidth;
   public int mTileHeight;
   public int mGridRow;
   public int mGridColumn;
   public String mSessionId;
}
