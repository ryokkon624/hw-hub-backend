package com.hwhub.backend.presentation.rest.code;

import com.hwhub.backend.application.service.CodeService;
import com.hwhub.backend.presentation.rest.code.dto.CodeDto;
import com.hwhub.backend.presentation.rest.code.dto.CodeListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeController {

  private final CodeService codeService;

  @GetMapping
  public CodeListResponse listCodes(
      @RequestParam(name = "codeType", required = false) String codeType) {
    var models = codeService.listCodes(codeType);
    List<CodeDto> dtos = models.stream().map(CodeDto::from).toList();

    CodeListResponse response = new CodeListResponse();
    response.setCodes(dtos);
    return response;
  }
}
