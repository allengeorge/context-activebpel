//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/list/AeCatalogListResult.java,v 1.1 2006/07/18 20:02:4
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

import java.util.Collection;

/**
 * Wraps the catalog listing results.
 */
public class AeCatalogListResult extends AeListResult
{
    /**
     * Constructor.
     * @param aTotalRowCount
     * @param aResults
     * @param aCompleteRowCount
     */
    public AeCatalogListResult(int aTotalRowCount, Collection aResults,
            boolean aCompleteRowCount)
    {
        super(aTotalRowCount, aResults, aCompleteRowCount);
    }
    
    /**
     * Accessor for catalog listing detail array.
     */
    public AeCatalogItem[] getDetails()
    {
        return (AeCatalogItem[])getResultsInternal().toArray( new AeCatalogItem[getResultsInternal().size()]);
    }
}
