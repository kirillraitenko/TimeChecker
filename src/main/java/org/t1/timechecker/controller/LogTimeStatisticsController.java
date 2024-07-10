package org.t1.timechecker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.t1.timechecker.annotation.TrackAsyncTime;
import org.t1.timechecker.services.logtime.LogTimeService;

import java.util.Map;

@RestController
@RequestMapping("/logs")
public class LogTimeStatisticsController {

    private final LogTimeService service;

    public LogTimeStatisticsController(LogTimeService service) {
        this.service = service;
    }

    @TrackAsyncTime
    @GetMapping("statistics")
    public Map<String, Object> getStatistics(@RequestParam String method) {
        return service.getAllMethodStatistics(method);
    }

    @TrackAsyncTime
    @GetMapping("statistics/count")
    public Map<String, Object> getStatisticsCount(@RequestParam String method) {
        return Map.of("count", service.countMethodСallsByNameMethod(method));
    }

    @TrackAsyncTime
    @GetMapping("statistics/min")
    public Map<String, Object> getStatisticsMin(@RequestParam String method) {
        return Map.of("min", service.getMinDurationByMethod(method));
    }

    @TrackAsyncTime
    @GetMapping("statistics/max")
    public Map<String, Object> getStatisticsMax(@RequestParam String method) {
        return Map.of("max", service.getMaxDurationByMethod(method));
    }

    @TrackAsyncTime
    @GetMapping("statistics/avg")
    public Map<String, Object> getStatisticsAvg(@RequestParam String method) {
        return Map.of("avg", service.getAverageDurationByMethod(method));
    }

    @TrackAsyncTime
    @GetMapping("methods")
    public Map<String, Object> getAllMethods() {
        return Map.of("methods", service.getAllMethods());
    }

    @TrackAsyncTime
    @GetMapping("types")
    public Map<String, Object> getAllTypes() {
        return Map.of("types", service.getAllTypes());
    }

    @TrackAsyncTime
    @GetMapping("statistics/all")
    public Map<String, Object> getAllStatistics() {
        return service.getAllStatistics();
    }
}
