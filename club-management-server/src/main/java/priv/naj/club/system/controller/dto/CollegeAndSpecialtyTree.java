package priv.naj.club.system.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollegeAndSpecialtyTree {
    private String label;
    private String value;
    private boolean disabled = false;
    private List<CollegeAndSpecialtyTree> children;
}
