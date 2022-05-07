package com.springboot.planning_poker.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Message {

    protected Long sender;
    protected String table;
    protected String content;
    protected MessageType messageType;
    protected String issue;
    public enum MessageType {
        JOIN,
        /* ---GAME--- */
        /// SELECTED CARD
        SELECTED,
        UNSELECTED_CARD,
        /// SPECTATOR MODE
        ACTIVE_SPECTATOR,
        DEACTIVATE_SPECTATOR,
        /// END GAME
        SHOW_CARD,
        /// RESTART GAME
        START_NEW_VOTE,
        /* ---ISSUE---- */
        ADD_ISSUE,
        SELECTED_ISSUE,
        UNSELECTED_ISSUE,
        IMPORT_FROM_URLS,
        IMPORT_FROM_CSV,
        DELETE_ALL_ISSUE,
        SELECTED_ISSUE,
        UNSELECTED_ISSUE,
        IMPORT_FROM_URLS,
        IMPORT_FROM_CSV,
        LEAVE
    }
}
