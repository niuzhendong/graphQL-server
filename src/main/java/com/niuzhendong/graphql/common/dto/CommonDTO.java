package com.niuzhendong.graphql.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class CommonDTO implements Serializable {
    public CommonDTO() {
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CommonDTO)) {
            return false;
        } else {
            CommonDTO other = (CommonDTO)o;
            return other.canEqual(this);
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CommonDTO;
    }

    public int hashCode() {
        return 1;
    }

    public String toString() {
        return "CommonDTO()";
    }
}
