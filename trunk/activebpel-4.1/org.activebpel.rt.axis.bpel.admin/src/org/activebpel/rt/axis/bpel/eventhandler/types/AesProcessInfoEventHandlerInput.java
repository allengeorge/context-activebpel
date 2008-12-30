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
package org.activebpel.rt.axis.bpel.eventhandler.types;

public class AesProcessInfoEventHandlerInput  implements java.io.Serializable {
    private long contextId;

    private long processId;

    private java.lang.String path;

    private int eventType;

    private java.lang.String faultName;

    private java.lang.String text;

    private java.util.Calendar timestamp;

    public AesProcessInfoEventHandlerInput() {
    }

    public AesProcessInfoEventHandlerInput(
           long contextId,
           long processId,
           java.lang.String path,
           int eventType,
           java.lang.String faultName,
           java.lang.String text,
           java.util.Calendar timestamp) {
           this.contextId = contextId;
           this.processId = processId;
           this.path = path;
           this.eventType = eventType;
           this.faultName = faultName;
           this.text = text;
           this.timestamp = timestamp;
    }


    /**
     * Gets the contextId value for this AesProcessInfoEventHandlerInput.
     * 
     * @return contextId
     */
    public long getContextId() {
        return contextId;
    }


    /**
     * Sets the contextId value for this AesProcessInfoEventHandlerInput.
     * 
     * @param contextId
     */
    public void setContextId(long contextId) {
        this.contextId = contextId;
    }


    /**
     * Gets the processId value for this AesProcessInfoEventHandlerInput.
     * 
     * @return processId
     */
    public long getProcessId() {
        return processId;
    }


    /**
     * Sets the processId value for this AesProcessInfoEventHandlerInput.
     * 
     * @param processId
     */
    public void setProcessId(long processId) {
        this.processId = processId;
    }


    /**
     * Gets the path value for this AesProcessInfoEventHandlerInput.
     * 
     * @return path
     */
    public java.lang.String getPath() {
        return path;
    }


    /**
     * Sets the path value for this AesProcessInfoEventHandlerInput.
     * 
     * @param path
     */
    public void setPath(java.lang.String path) {
        this.path = path;
    }


    /**
     * Gets the eventType value for this AesProcessInfoEventHandlerInput.
     * 
     * @return eventType
     */
    public int getEventType() {
        return eventType;
    }


    /**
     * Sets the eventType value for this AesProcessInfoEventHandlerInput.
     * 
     * @param eventType
     */
    public void setEventType(int eventType) {
        this.eventType = eventType;
    }


    /**
     * Gets the faultName value for this AesProcessInfoEventHandlerInput.
     * 
     * @return faultName
     */
    public java.lang.String getFaultName() {
        return faultName;
    }


    /**
     * Sets the faultName value for this AesProcessInfoEventHandlerInput.
     * 
     * @param faultName
     */
    public void setFaultName(java.lang.String faultName) {
        this.faultName = faultName;
    }


    /**
     * Gets the text value for this AesProcessInfoEventHandlerInput.
     * 
     * @return text
     */
    public java.lang.String getText() {
        return text;
    }


    /**
     * Sets the text value for this AesProcessInfoEventHandlerInput.
     * 
     * @param text
     */
    public void setText(java.lang.String text) {
        this.text = text;
    }


    /**
     * Gets the timestamp value for this AesProcessInfoEventHandlerInput.
     * 
     * @return timestamp
     */
    public java.util.Calendar getTimestamp() {
        return timestamp;
    }


    /**
     * Sets the timestamp value for this AesProcessInfoEventHandlerInput.
     * 
     * @param timestamp
     */
    public void setTimestamp(java.util.Calendar timestamp) {
        this.timestamp = timestamp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AesProcessInfoEventHandlerInput)) return false;
        AesProcessInfoEventHandlerInput other = (AesProcessInfoEventHandlerInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.contextId == other.getContextId() &&
            this.processId == other.getProcessId() &&
            ((this.path==null && other.getPath()==null) || 
             (this.path!=null &&
              this.path.equals(other.getPath()))) &&
            this.eventType == other.getEventType() &&
            ((this.faultName==null && other.getFaultName()==null) || 
             (this.faultName!=null &&
              this.faultName.equals(other.getFaultName()))) &&
            ((this.text==null && other.getText()==null) || 
             (this.text!=null &&
              this.text.equals(other.getText()))) &&
            ((this.timestamp==null && other.getTimestamp()==null) || 
             (this.timestamp!=null &&
              this.timestamp.equals(other.getTimestamp())));
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
        _hashCode += new Long(getContextId()).hashCode();
        _hashCode += new Long(getProcessId()).hashCode();
        if (getPath() != null) {
            _hashCode += getPath().hashCode();
        }
        _hashCode += getEventType();
        if (getFaultName() != null) {
            _hashCode += getFaultName().hashCode();
        }
        if (getText() != null) {
            _hashCode += getText().hashCode();
        }
        if (getTimestamp() != null) {
            _hashCode += getTimestamp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
