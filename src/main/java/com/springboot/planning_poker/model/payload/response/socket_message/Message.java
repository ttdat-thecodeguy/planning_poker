package com.springboot.planning_poker.model.payload.response.socket_message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Message {
    protected Long sender;
    protected String table;
    protected String content;
    private boolean spectator;
    protected MessageType messageType;
    protected String issue;
    public enum MessageType {
        JOIN,
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
        START_NEW_VOTE_WITH_ISSUE,
        /* ---ISSUE---- */
        ADD_ISSUE,
        DELETE_ALL_ISSUE,
        SELECTED_ISSUE,
        UNSELECTED_ISSUE,
        IMPORT_FROM_URLS,
        IMPORT_FROM_CSV,
        LEAVE

    }
}
