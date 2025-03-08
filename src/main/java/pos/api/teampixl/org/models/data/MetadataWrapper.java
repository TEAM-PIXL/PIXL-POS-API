package pos.api.teampixl.org.models.data;

import java.util.Map;
import java.util.Objects;

/*================================================================================================================
 MetadataWrapper is a wrapper class that encapsulates a map of metadata. It is used to store metadata for a record 
 in the database. The class is immutable and unmodifiable, and it implements the Comparable interface to allow for
 sorting based on a unique identifier, such as the id field. The class also overrides the equals, hashCode, and to
 String methods.
 ===============================================================================================================*/


/**
 * A wrapper class that encapsulates a map of metadata for a public record.
 * The class is immutable and unmodifiable. It implements the Comparable interface to allow for sorting based on 
 * a unique identifier. The class also overrides the equals, hashCode, and toString methods.
 * @param metadata the map of metadata for a public record
 */
public record MetadataWrapper(Map<String, Object> metadata) implements Comparable<MetadataWrapper> {

    /**
     * Constructs a new MetadataWrapper with the specified metadata.
     * @param metadata the map of metadata for a public record
     */
    public MetadataWrapper {
        metadata = Map.copyOf(metadata);
    }

    /**
     * Compares this MetadataWrapper with another MetadataWrapper based on the unique identifier (id) in the metadata.
     * 
     * @param other the MetadataWrapper to compare
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, 
     * or greater than the specified object
     */
    @Override
    public int compareTo(MetadataWrapper other) {
        String thisId = (String) this.metadata.get("id");
        String otherId = (String) other.metadata.get("id");

        if (thisId == null || otherId == null) {
            throw new IllegalStateException("Metadata id cannot be null");
        }

        return thisId.compareTo(otherId);
    }


    /**
     * Returns boolean value based on whether the specified object is equal to this MetadataWrapper.
     * 
     * @param o the object to compare
     * @return true if the specified object is equal to this MetadataWrapper, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetadataWrapper that = (MetadataWrapper) o;
        return Objects.equals(metadata, that.metadata);
    }

    /**
     * Returns the hash code value for this MetadataWrapper.
     * 
     * @return the hash code value for this MetadataWrapper
     */
    @Override
    public int hashCode() {
        return Objects.hash(metadata);
    }

    /**
     * Returns the string representation of this MetadataWrapper.
     * 
     * @return the string representation of this MetadataWrapper
     */
    @Override
    public String toString() {
        return metadata.toString();
    }

}
