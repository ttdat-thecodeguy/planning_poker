package com.springboot.planning_poker.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Message {

    private Long sender;
    private String table;
    private String content;
    private MessageType messageType;

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
        START_NEW_VOTE_WITH_ISSUE,

        /* ---ISSUE---- */
        ADD_ISSUE,
        DELETE_ALL_ISSUE,
        LEAVE
    }
}
