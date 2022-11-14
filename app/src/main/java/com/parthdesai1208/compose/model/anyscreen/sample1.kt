package com.parthdesai1208.compose.model.anyscreen

import androidx.annotation.DrawableRes
import com.parthdesai1208.compose.R

data class ReplyHomeUIState(
    val emails: List<Email> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)

data class Email(
    val id: Long,
    val sender: Account,
    val recipients: List<Account> = emptyList(),
    val subject: String = "",
    val body: String = "",
    val attachments: List<EmailAttachment> = emptyList(),
    var isImportant: Boolean = false,
    var isStarred: Boolean = false,
    var mailbox: MailboxType = MailboxType.INBOX,
    var createAt: String,
    val threads: List<Email> = emptyList()
)

data class Account(
    val id: Long,
    val uid: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val altEmail: String,
    @DrawableRes val avatar: Int,
    var isCurrentAccount: Boolean = false
) {
    //used in profile image description
    val fullName: String = "$firstName $lastName"
}

data class EmailAttachment(
    @DrawableRes val resId: Int,
    val contentDesc: String
)

enum class MailboxType {
    INBOX, DRAFTS, SENT, SPAM, TRASH
}

object LocalEmailsDataProvider {

    private val threads = listOf(
        Email(
            4L,
            LocalAccountsDataProvider.getContactAccountByUid(11L),
            listOf(
                LocalAccountsDataProvider.getDefaultUserAccount(),
                LocalAccountsDataProvider.getContactAccountByUid(8L),
                LocalAccountsDataProvider.getContactAccountByUid(5L)
            ),
            "Brazil trip",
            """
                Thought we might be able to go over some details about our upcoming vacation.

                I've been doing a bit of research and have come across a few paces in Northern Brazil that I think we should check out. One, the north has some of the most predictable wind on the planet. I'd love to get out on the ocean and kitesurf for a couple of days if we're going to be anywhere near or around Taiba. I hear it's beautiful there and if you're up for it, I'd love to go. Other than that, I haven't spent too much time looking into places along our road trip route. I'm assuming we can find places to stay and things to do as we drive and find places we think look interesting. But... I know you're more of a planner, so if you have ideas or places in mind, lets jot some ideas down!

                Maybe we can jump on the phone later today if you have a second.
            """.trimIndent(),
            createAt = "2 hours ago",
            isStarred = true
        ),
        Email(
            5L,
            LocalAccountsDataProvider.getContactAccountByUid(13L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Update to Your Itinerary",
            "",
            createAt = "2 hours ago",
        ),
        Email(
            6L,
            LocalAccountsDataProvider.getContactAccountByUid(10L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Recipe to try",
            "Raspberry Pie: We should make this pie recipe tonight! The filling is " +
                    "very quick to put together.",
            createAt = "2 hours ago",
            mailbox = MailboxType.SENT
        ),
        Email(
            7L,
            LocalAccountsDataProvider.getContactAccountByUid(9L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Delivered",
            "Your shoes should be waiting for you at home!",
            createAt = "2 hours ago",
        ),
        Email(
            8L,
            LocalAccountsDataProvider.getContactAccountByUid(13L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Your update on Google Play Store is live!",
            """
              Your update, 0.1.1, is now live on the Play Store and available for your alpha users to start testing.
              
              Your alpha testers will be automatically notified. If you'd rather send them a link directly, go to your Google Play Console and follow the instructions for obtaining an open alpha testing link.
          """.trimIndent(),
            mailbox = MailboxType.TRASH,
            createAt = "3 hours ago",
        ),
        Email(
            9L,
            LocalAccountsDataProvider.getContactAccountByUid(10L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "(No subject)",
            """
            Hey, 
            
            Wanted to email and see what you thought of
          """.trimIndent(),
            createAt = "3 hours ago",
            mailbox = MailboxType.DRAFTS
        )
    )

    val allEmails = mutableListOf(
        Email(
            0L,
            LocalAccountsDataProvider.getContactAccountByUid(9L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Package shipped!",
            """
                Cucumber Mask Facial has shipped.

                Keep an eye out for a package to arrive between this Thursday and next Tuesday. If for any reason you don't receive your package before the end of next week, please reach out to us for details on your shipment.

                As always, thank you for shopping with us and we hope you love our specially formulated Cucumber Mask!
            """.trimIndent(),
            createAt = "20 mins ago",
            isStarred = true,
            threads = threads,
        ),
        Email(
            1L,
            LocalAccountsDataProvider.getContactAccountByUid(6L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Brunch this weekend?",
            """
                I'll be in your neighborhood doing errands and was hoping to catch you for a coffee this Saturday. If you don't have anything scheduled, it would be great to see you! It feels like its been forever.

                If we do get a chance to get together, remind me to tell you about Kim. She stopped over at the house to say hey to the kids and told me all about her trip to Mexico.

                Talk to you soon,

                Ali
            """.trimIndent(),
            createAt = "40 mins ago",
            threads = threads,
        ),
        Email(
            2L,
            LocalAccountsDataProvider.getContactAccountByUid(5L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Bonjour from Paris",
            "Here are some great shots from my trip...",
            listOf(
                EmailAttachment(R.drawable.paris_1, "Bridge in Paris"),
                EmailAttachment(R.drawable.paris_2, "Bridge in Paris at night"),
                EmailAttachment(R.drawable.paris_3, "City street in Paris"),
                EmailAttachment(R.drawable.paris_4, "Street with bike in Paris")
            ),
            true,
            createAt = "1 hour ago",
            threads = threads,
        ),
        Email(
            3L,
            LocalAccountsDataProvider.getContactAccountByUid(8L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "High school reunion?",
            """
                Hi friends,

                I was at the grocery store on Sunday night.. when I ran into Genie Williams! I almost didn't recognize her afer 20 years!

                Anyway, it turns out she is on the organizing committee for the high school reunion this fall. I don't know if you were planning on going or not, but she could definitely use our help in trying to track down lots of missing alums. If you can make it, we're doing a little phone-tree party at her place next Saturday, hoping that if we can find one person, thee more will...
            """.trimIndent(),
            createAt = "2 hours ago",
            mailbox = MailboxType.SENT
        ),
        Email(
            4L,
            LocalAccountsDataProvider.getContactAccountByUid(11L),
            listOf(
                LocalAccountsDataProvider.getDefaultUserAccount(),
                LocalAccountsDataProvider.getContactAccountByUid(8L),
                LocalAccountsDataProvider.getContactAccountByUid(5L)
            ),
            "Brazil trip",
            """
                Thought we might be able to go over some details about our upcoming vacation.

                I've been doing a bit of research and have come across a few paces in Northern Brazil that I think we should check out. One, the north has some of the most predictable wind on the planet. I'd love to get out on the ocean and kitesurf for a couple of days if we're going to be anywhere near or around Taiba. I hear it's beautiful there and if you're up for it, I'd love to go. Other than that, I haven't spent too much time looking into places along our road trip route. I'm assuming we can find places to stay and things to do as we drive and find places we think look interesting. But... I know you're more of a planner, so if you have ideas or places in mind, lets jot some ideas down!

                Maybe we can jump on the phone later today if you have a second.
            """.trimIndent(),
            createAt = "2 hours ago",
            isStarred = true
        ),
        Email(
            5L,
            LocalAccountsDataProvider.getContactAccountByUid(13L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Update to Your Itinerary",
            "",
            createAt = "2 hours ago",
        ),
        Email(
            6L,
            LocalAccountsDataProvider.getContactAccountByUid(10L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Recipe to try",
            "Raspberry Pie: We should make this pie recipe tonight! The filling is " +
                    "very quick to put together.",
            createAt = "2 hours ago",
            mailbox = MailboxType.SENT
        ),
        Email(
            7L,
            LocalAccountsDataProvider.getContactAccountByUid(9L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Delivered",
            "Your shoes should be waiting for you at home!",
            createAt = "2 hours ago",
        ),
        Email(
            8L,
            LocalAccountsDataProvider.getContactAccountByUid(13L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Your update on Google Play Store is live!",
            """
              Your update, 0.1.1, is now live on the Play Store and available for your alpha users to start testing.
              
              Your alpha testers will be automatically notified. If you'd rather send them a link directly, go to your Google Play Console and follow the instructions for obtaining an open alpha testing link.
          """.trimIndent(),
            mailbox = MailboxType.TRASH,
            createAt = "3 hours ago",
        ),
        Email(
            9L,
            LocalAccountsDataProvider.getContactAccountByUid(10L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "(No subject)",
            """
            Hey, 
            
            Wanted to email and see what you thought of
          """.trimIndent(),
            createAt = "3 hours ago",
            mailbox = MailboxType.DRAFTS
        ),
        Email(
            10L,
            LocalAccountsDataProvider.getContactAccountByUid(5L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Try a free TrailGo account",
            """
            Looking for the best hiking trails in your area? TrailGo gets you on the path to the outdoors faster than you can pack a sandwich. 
            
            Whether you're an experienced hiker or just looking to get outside for the afternoon, there's a segment that suits you.
          """.trimIndent(),
            createAt = "3 hours ago",
            mailbox = MailboxType.TRASH
        ),
        Email(
            11L,
            LocalAccountsDataProvider.getContactAccountByUid(5L),
            listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
            "Free money",
            """
            You've been selected as a winner in our latest raffle! To claim your prize, click on the link.
          """.trimIndent(),
            createAt = "3 hours ago",
            mailbox = MailboxType.SPAM
        )
    )

    /**
     * Get an [Email] with the given [id].
     */
    fun get(id: Long): Email? {
        return allEmails.firstOrNull { it.id == id }
    }

    /**
     * Create a new, blank [Email].
     */
    fun create(): Email {
        return Email(
            System.nanoTime(), // Unique ID generation.
            LocalAccountsDataProvider.getDefaultUserAccount(),
            createAt = "Just now",
        )
    }

    /**
     * Create a new [Email] that is a reply to the email with the given [replyToId].
     */
    fun createReplyTo(replyToId: Long): Email {
        val replyTo = get(replyToId) ?: return create()
        return Email(
            id = System.nanoTime(),
            sender = replyTo.recipients.firstOrNull()
                ?: LocalAccountsDataProvider.getDefaultUserAccount(),
            recipients = listOf(replyTo.sender) + replyTo.recipients,
            subject = replyTo.subject,
            isStarred = replyTo.isStarred,
            isImportant = replyTo.isImportant,
            createAt = "Just now",
        )
    }


    /**
     * Get a list of [EmailFolder]s by which [Email]s can be categorized.
     */
    fun getAllFolders() = listOf(
        "Receipts",
        "Pine Elementary",
        "Taxes",
        "Vacation",
        "Mortgage",
        "Grocery coupons"
    )
}

object LocalAccountsDataProvider {

    val allUserAccounts = mutableListOf(
        Account(
            1L,
            0L,
            "Jeff",
            "Hansen",
            "hikingfan@gmail.com",
            "hkngfan@outside.com",
            R.drawable.avatar_10,
            true
        ),
        Account(
            2L,
            0L,
            "Jeff",
            "H",
            "jeffersonloveshiking@gmail.com",
            "jeffersonloveshiking@work.com",
            R.drawable.avatar_2
        ),
        Account(
            3L,
            0L,
            "Jeff",
            "Hansen",
            "jeffersonc@google.com",
            "jeffersonc@gmail.com",
            R.drawable.avatar_9
        )
    )

    private val allUserContactAccounts = listOf(
        Account(
            4L,
            1L,
            "Tracy",
            "Alvarez",
            "tracealvie@gmail.com",
            "tracealvie@gravity.com",
            R.drawable.avatar_1
        ),
        Account(
            5L,
            2L,
            "Allison",
            "Trabucco",
            "atrabucco222@gmail.com",
            "atrabucco222@work.com",
            R.drawable.avatar_3
        ),
        Account(
            6L,
            3L,
            "Ali",
            "Connors",
            "aliconnors@gmail.com",
            "aliconnors@android.com",
            R.drawable.avatar_5
        ),
        Account(
            7L,
            4L,
            "Alberto",
            "Williams",
            "albertowilliams124@gmail.com",
            "albertowilliams124@chromeos.com",
            R.drawable.avatar_0
        ),
        Account(
            8L,
            5L,
            "Kim",
            "Alen",
            "alen13@gmail.com",
            "alen13@mountainview.gov",
            R.drawable.avatar_7
        ),
        Account(
            9L,
            6L,
            "Google",
            "Express",
            "express@google.com",
            "express@gmail.com",
            R.drawable.avatar_express
        ),
        Account(
            10L,
            7L,
            "Sandra",
            "Adams",
            "sandraadams@gmail.com",
            "sandraadams@textera.com",
            R.drawable.avatar_2
        ),
        Account(
            11L,
            8L,
            "Trevor",
            "Hansen",
            "trevorhandsen@gmail.com",
            "trevorhandsen@express.com",
            R.drawable.avatar_8
        ),
        Account(
            12L,
            9L,
            "Sean",
            "Holt",
            "sholt@gmail.com",
            "sholt@art.com",
            R.drawable.avatar_6
        ),
        Account(
            13L,
            10L,
            "Frank",
            "Hawkins",
            "fhawkank@gmail.com",
            "fhawkank@thisisme.com",
            R.drawable.avatar_4
        )
    )

    /**
     * Get the current user's default account.
     */
    fun getDefaultUserAccount() = allUserAccounts.first()

    /**
     * Whether or not the given [Account.id] uid is an account owned by the current user.
     */
    fun isUserAccount(uid: Long): Boolean = allUserAccounts.any { it.uid == uid }


    /**
     * Get the contact of the current user with the given [accountId].
     */
    fun getContactAccountByUid(accountId: Long): Account {
        return allUserContactAccounts.firstOrNull { it.id == accountId }
            ?: allUserContactAccounts.first()
    }
}
