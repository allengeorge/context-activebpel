//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/list/AeCatalogItemPlanReference.java,v 1.1 2006/07/18 20:02:4
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
package org.activebpel.rt.bpel.impl.list;

import javax.xml.namespace.QName;

/**
 * Wraps the plan detail information (QName) for plans
 * that are associated with a catalog item detail object.
 */
public class AeCatalogItemPlanReference
{
    /** Plan QName. */
    protected QName mPlanQName;
    
    /**
     * Constructor.
     * @param aQName
     */
    public AeCatalogItemPlanReference( QName aQName )
    {
        mPlanQName = aQName;
    }
    
    /**
     * Accessor for the plan QName.
     */
    public QName getPlanQName()
    {
        return mPlanQName;
    }
}
