package ru.dvkombarov.app.service;

import ru.dvkombarov.app.rest.dto.VulnerInfoDto;

import javax.annotation.Nonnull;
import java.util.List;

public interface VulnersService {
  List<VulnerInfoDto> getVulnersInfoByCveList(@Nonnull List<String> cveList);
}
