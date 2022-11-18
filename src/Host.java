/**
 * A class representing a Host that has an ipAddress and
 * that may have a domainName. If there is no domain name,
 * a Host is identified by its ipAddress. Hosts make no
 * differentiation between identical Hosts with different
 * tries values. Hosts do differentiate between two Hosts
 * sharing an ipAddress but with one missing a domainName.
 * @param ipAddress
 * @param domainName
 * @param tries
 */
public record Host (String ipAddress, String domainName, int tries) {

    /**
     * A method to define how two Hosts are equal to each other.
     * @param other   the reference object with which to compare
     * @return true if other is a Host sharing a
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Host) {
            /*
             * Return true if these two Hosts share the
             * same name (domain name or ip address, if
             * domain is not available).
             */
            return ((Host) other).toString()
                    .equals(this.toString());
        }
        return false;
    }

    /**
     * A method to return the domain name of this Host
     * or the ip address of this Host if the domain is
     * unavailable.
     * @return a String representing this Host
     */
    @Override
    public String toString() {
        return (this.domainName != null)
                ? this.domainName
                : this.ipAddress;
    }
}
