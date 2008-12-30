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

public class AesGetVariableDataType  implements java.io.Serializable {
    private long pid;

    private java.lang.String variablePath;

    public AesGetVariableDataType() {
    }

    public AesGetVariableDataType(
           long pid,
           java.lang.String variablePath) {
           this.pid = pid;
           this.variablePath = variablePath;
    }


    /**
     * Gets the pid value for this AesGetVariableDataType.
     * 
     * @return pid
     */
    public long getPid() {
        return pid;
    }


    /**
     * Sets the pid value for this AesGetVariableDataType.
     * 
     * @param pid
     */
    public void setPid(long pid) {
        this.pid = pid;
    }


    /**
     * Gets the variablePath value for this AesGetVariableDataType.
     * 
     * @return variablePath
     */
    public java.lang.String getVariablePath() {
        return variablePath;
    }


    /**
     * Sets the variablePath value for this AesGetVariableDataType.
     * 
     * @param variablePath
     */
    public void setVariablePath(java.lang.String variablePath) {
        this.variablePath = variablePath;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesGetVariableDataType)) return false;
        AesGetVariableDataType other = (AesGetVariableDataType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.pid == other.getPid() &&
            ((this.variablePath==null && other.getVariablePath()==null) || 
             (this.variablePath!=null &&
              this.variablePath.equals(other.getVariablePath())));
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
        if (getVariablePath() != null) {
            _hashCode += getVariablePath().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
