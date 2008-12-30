// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/lock/IAeLockerSerializationNames.java,v 1.3 2005/02/01 19:53:0
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
package org.activebpel.rt.bpel.impl.lock;

/**
 * Defines constants for <code>AeLockerSerializer</code> and
 * <code>AeLockerDeserializer</code>.
 */
interface IAeLockerSerializationNames
{
   public static final String TAG_LOCK          = "lock"; //$NON-NLS-1$
   public static final String TAG_LOCKS         = "locks"; //$NON-NLS-1$
   public static final String TAG_OWNER         = "owner"; //$NON-NLS-1$
   public static final String TAG_REQUEST       = "request"; //$NON-NLS-1$
   public static final String TAG_REQUESTS      = "requests"; //$NON-NLS-1$
   public static final String TAG_ROOT          = "variableLocker"; //$NON-NLS-1$
   public static final String TAG_VARIABLE      = "variable"; //$NON-NLS-1$
   public static final String ATTR_EXCLUSIVE    = "exclusive"; //$NON-NLS-1$
   public static final String ATTR_OWNERPATH    = "ownerPath"; //$NON-NLS-1$
   public static final String ATTR_VARIABLEPATH = "variablePath"; //$NON-NLS-1$
}
