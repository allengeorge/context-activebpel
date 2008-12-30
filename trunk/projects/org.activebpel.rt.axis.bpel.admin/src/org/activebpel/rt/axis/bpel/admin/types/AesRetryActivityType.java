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
package org.activebpel.rt.axis.bpel.admin.types;

public class AesRetryActivityType  implements java.io.Serializable {
    private long pid;

    private java.lang.String locationPath;

    private boolean atScope;

    public AesRetryActivityType() {
    }

    public AesRetryActivityType(
           long pid,
           java.lang.String locationPath,
           boolean atScope) {
           this.pid = pid;
           this.locationPath = locationPath;
           this.atScope = atScope;
    }


    /**
     * Gets the pid value for this AesRetryActivityType.
     * 
     * @return pid
     */
    public long getPid() {
        return pid;
    }


    /**
     * Sets the pid value for this AesRetryActivityType.
     * 
     * @param pid
     */
    public void setPid(long pid) {
        this.pid = pid;
    }


    /**
     * Gets the locationPath value for this AesRetryActivityType.
     * 
     * @return locationPath
     */
    public java.lang.String getLocationPath() {
        return locationPath;
    }


    /**
     * Sets the locationPath value for this AesRetryActivityType.
     * 
     * @param locationPath
     */
    public void setLocationPath(java.lang.String locationPath) {
        this.locationPath = locationPath;
    }


    /**
     * Gets the atScope value for this AesRetryActivityType.
     * 
     * @return atScope
     */
    public boolean isAtScope() {
        return atScope;
    }


    /**
     * Sets the atScope value for this AesRetryActivityType.
     * 
     * @param atScope
     */
    public void setAtScope(boolean atScope) {
        this.atScope = atScope;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesRetryActivityType)) return false;
        AesRetryActivityType other = (AesRetryActivityType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.pid == other.getPid() &&
            ((this.locationPath==null && other.getLocationPath()==null) || 
             (this.locationPath!=null &&
              this.locationPath.equals(other.getLocationPath()))) &&
            this.atScope == other.isAtScope();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += new Long(getPid()).hashCode();
        if (getLocationPath() != null) {
            _hashCode += getLocationPath().hashCode();
        }
        _hashCode += (isAtScope() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

}
