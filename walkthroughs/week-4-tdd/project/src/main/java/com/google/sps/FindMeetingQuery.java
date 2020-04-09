// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // If there are no attendees in the request, then return the whole day.
    if (request.getAttendees().isEmpty()) {
        return Arrays.asList(TimeRange.WHOLE_DAY);
    }
    // If the request is longer than the entire day, then return an empty list.
    if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) {
        return Arrays.asList();
    }
    // If there are no events, then return the whole day.
    if (events.isEmpty()) {
        return Arrays.asList(TimeRange.WHOLE_DAY);
    }
    // variables needed throughout the function
    Collection<String> request_attendees_list = request.getAttendees();
    Collection<Event> event_list = new ArrayList<Event>();
    // Loops through the events parameter to add to an event_list collection
    // if the request attendees is related to the attendees of the event. If
    // the event attendees do not match the request attendees, then the event
    // is ignored since it is not related to the request. 
    for (Event event : events) {
        for (String event_request : request_attendees_list) {
             if (event.getAttendees().contains(event_request)) {
                 event_list.add(event);
                 break;
             }
        }
    }
    // After looping for related events, if there is none, then return the whole day. 
    if (event_list.size() == 0) {
        return Arrays.asList(TimeRange.WHOLE_DAY);
    }
    // If there is a single related event, return the range from before and after the event. 
    if (event_list.size() == 1) {
        List<TimeRange> single_time_range_list = new ArrayList<TimeRange>();
        for (Event event : event_list) {
            single_time_range_list.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, event.getWhen().start(), false));
            single_time_range_list.add(TimeRange.fromStartEnd(event.getWhen().end(), TimeRange.END_OF_DAY, true));
        }
        return single_time_range_list;
    }
    // If there are more than one related event, then follow more specific steps below. 
    if (event_list.size() > 1) {
        List<TimeRange> multiple_time_range_list = new ArrayList<TimeRange>();
        List<Integer> start_time_list = new ArrayList<Integer>();
        List<Integer> end_time_list = new ArrayList<Integer>();
        // Add the start and end times for each event into a list. 
        for (Event event : event_list) {
            start_time_list.add(event.getWhen().start());
            end_time_list.add(event.getWhen().end());
        }
        // If the starting time is not 0, then create the first time range from
        // the morning to the first element of start_time_list. 
        if (start_time_list.get(0) != 0) {
            multiple_time_range_list.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, start_time_list.get(0), false));
        }
        // Loop to check to see if the first element in end_time_list at i is less 
        // than the start_time_list at i+1. If the statement is true, then
        // a time range is added ranging from the element of end_time_list at 
        // i to the start_time i+1. Otherwise, the for loop skips it. This
        // loop checks until the second to last element in end_time_list. 
        for (int i = 0; i < end_time_list.size()-1; i++) {
            if (end_time_list.get(i) < start_time_list.get(i+1)) {
                multiple_time_range_list.add(TimeRange.fromStartEnd(end_time_list.get(i), start_time_list.get(i+1), false));
            } else {
                continue;
            }
        }
        // If the last element of the end_time_list is not 1440, then the
        // for loop checks for the latest end time. Afterwards, the last 
        // time range is added with the latest ending time. 
        if (end_time_list.get(end_time_list.size()-1) != 1440) {
            int latest = end_time_list.get(0);
            for (int i = 0; i < end_time_list.size(); i++) {
                if (latest < end_time_list.get(i)) {
                    latest = end_time_list.get(i);
                }
            }
            multiple_time_range_list.add(TimeRange.fromStartEnd(latest, TimeRange.END_OF_DAY, true));
        } 
        return multiple_time_range_list;
    }
    // If none of the above match, then return an empty collection. 
    return Arrays.asList();
  }
}
