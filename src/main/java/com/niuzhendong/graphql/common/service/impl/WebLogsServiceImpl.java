package com.niuzhendong.graphql.common.service.impl;

import com.niuzhendong.graphql.common.base.BaseServiceImpl;
import com.niuzhendong.graphql.common.domain.WebLogs;
import com.niuzhendong.graphql.common.domain.WebLogsRepository;
import com.niuzhendong.graphql.common.dto.WebLogsDTO;
import com.niuzhendong.graphql.common.service.IWebLogsService;
import org.springframework.stereotype.Service;

@Service
public class WebLogsServiceImpl extends BaseServiceImpl<WebLogsRepository, WebLogs, WebLogsDTO> implements IWebLogsService<WebLogs> {
    public WebLogsServiceImpl() {
    }
}
