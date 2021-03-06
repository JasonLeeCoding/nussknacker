import moment from "moment"
import {dateFormat} from "../../config"
import HttpService from "../../http/HttpService"
import {ThunkAction} from "../reduxTypes"
import {ProcessCounts} from "../../reducers/graph"

export function displayProcessCounts(processCounts: ProcessCounts): $TodoType {
  return {
    type: "DISPLAY_PROCESS_COUNTS",
    processCounts,
  }
}

export function fetchAndDisplayProcessCounts(processName: string, from: moment.Moment, to: moment.Moment): ThunkAction {
  return (dispatch) => HttpService.fetchProcessCounts(
    processName,
    from ? from.format(dateFormat) : null,
    to ? to.format(dateFormat) : null,
  ).then((response) => dispatch(displayProcessCounts(response.data)))
}
