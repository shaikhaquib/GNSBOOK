package com.digital.gnsbook.Config;

public class APIs {

    /*public static String DevDomain  = "https://development.gnsbook.com/api/";
    public static String Domain     = "https://development.gnsbook.com/";*/
    public static String DevDomain  = "https://www.gnsbook.com/api/";
    public static String Domain = "https://www.gnsbook.com/";

    public static String LoginAPI                               = Domain+"login";
    public static String UpdatePassword                         = Domain+"update_password";
    public static String RegisterAPI                            = Domain+"aap_signup";

    public static String Banner                                 = Domain+"bpic/";
    public static String Dp                                     = Domain+"dpic/";
    public static String postImg                                = Domain+"dpic/";


    public static String ACTIVEBenificiarylist                  = DevDomain+"c_activate_beneficiary";
    public static String Addebenificiary                        = DevDomain+"dmr_beneficiary_reg";
    public static String Addebenificiary_new                    = DevDomain+"beneficiary_check";
    public static String Addebenificiaryotp                     = DevDomain+"dmr_beneficiary_reg_validate";
    public static String AgenStatus                             = DevDomain+"agent_status_display";
    public static String AgentSignup                            = DevDomain+"dmr_signup";
    public static String Balance                                = DevDomain+"balance";
    public static String Binary                                 = DevDomain+"binary_data";



    public static String Corporate_ACTIVEBenificiarylist        = DevDomain+"cdmr_detail";
    public static String Corporate_Addebenificiary              = DevDomain+"cdmr_beneficiary_reg";
    public static String Corporate_AgentSignup                  = DevDomain+"cdmr_signup";
    public static String Corporate_Benificiary_valid            = DevDomain+"cdmr_beneficiary_verify";
    public static String Corporate_Benificiarylist              = DevDomain+"cdmr_detail";
    public static String Corporate_FundTransAPi                 = DevDomain+"cdmr_transfer";
    public static String Benificiarylist                        = DevDomain+"dmr_detail";

    public static String EmailVerification                      = DevDomain+"mail_verification";

    public static String FundTransAPi                           = DevDomain+"dmr_transfer";
    public static String Jolo_Status                            = DevDomain+"jolo_status";
    public static String Jolo_soft_balance                      = DevDomain+"jolo_soft_balance";
    public static String Jolo_soft_dmr_beneficiary_reg          = DevDomain+"jolo_soft_dmr_beneficiary_reg";
    public static String Jolo_soft_dmr_beneficiary_reg_validate = DevDomain+"jolo_soft_dmr_beneficiary_reg_validate";
    public static String BeniDelete                             = DevDomain+"";

    public static String Jolo_soft_dmr_details                  = DevDomain+"jolo_soft_dmr_detail";
    public static String Jolo_soft_dmr_signup                   = DevDomain+"jolo_soft_dmr_signup";
    public static String Jolo_soft_dmr_signup_validation        = DevDomain+"jolo_soft_dmr_signup_validate";
    public static String Manultransfer                          = DevDomain+"manually_jolo_transfer";


    public static String PhoneVerification                      = DevDomain+"sms_verification";
    public static String Phoneotpverifie                        = DevDomain+"sms_check";
    public static String PooLenter                              = DevDomain+"claime_pool";
    public static String PoolDATA                               = DevDomain+"pool_data";
    public static String ProfileDetail                          = DevDomain+"profile";
    public static String Statistics                             = DevDomain+"statistics_data";
    public static String UpadteCapping                          = DevDomain+"claimcapping";
    public static String UpdateProfile                          = DevDomain+"profile_update";
    public static String UploadPost                             = DevDomain+"add_timeline_by_companies";
    public static String UserPoolDATA                           = DevDomain+"pool_participants_by_customer_id";
    public static String Verificationstatus                     = DevDomain+"verify_data";
    public static String addmoney                               = DevDomain+"add_balane_online";
    public static String company_data_by_id                     = DevDomain+"company_data_by_id";
    public static String company_timeline                       = DevDomain+"newtimeline_data_by_company";
    public static String companydataAPI                         = DevDomain+"company_data";
    public static String contactUpadte                          = DevDomain+"profile_update_verify";
    public static String createpage                             = DevDomain+"add_company";
    public static String freinds                                = DevDomain+"friends";
    public static String jolo_soft_dmr_beneficiary_reg_otp      = DevDomain+"jolo_soft_dmr_beneficiary_reg_otp";
    public static String jolo_soft_dmr_transfer                 = DevDomain+"jolo_soft_dmr_transfer";
    public static String offline                                = DevDomain+"add_balane_offline";
    public static String reSendOtp                              = DevDomain+"dmr_beneficiary_reg_validate";
    public static String timelineAPI                            = DevDomain+"timeline_data_selflike";
    public static String timelineAPI_byid                       = DevDomain+"timeline_data_by_id";
    public static String topperformerAPI                        = DevDomain+"top_performer";
    public static String transaction_detail                     = DevDomain+"recent_transaction";
    public static String update_email                           = DevDomain+"profile_update_verify_email";
    public static String update_mobile                          = DevDomain+"profile_update_verify_mobile";
    public static String uploadDP                               = DevDomain+"upload_profile_dpic";
    public static String uploadbanner                           = DevDomain+"upload_profile_bpic";

    public static String uploadbannercomponyPage                = DevDomain+"upload_banner";

    // Like Unlike

    public static String Dolike                                 = DevDomain+"add_new_like_by_post";
    public static String Unlike                                 = DevDomain+"remove_new_like_by_post";

    public static String oldDolike                              = DevDomain+"add_like_by_post";
    public static String oldUnlike                              = DevDomain+"remove_like_by_post";

    public static String ChatFriend                             = DevDomain+"display_friend_list";

    public static String Corporate_AgentVr                      = DevDomain+"cdmr_signup_verify";
    public static String Comment_data                           = DevDomain+"comment_data_display";
    public static String DoComment                              = DevDomain+"add_new_comment_by_post";
    public static String old_DoComment                          = DevDomain+"add_comment_by_post";

    public static String SubPlan                                = DevDomain+"subscribe_plan";
    public static String Createplane                            = DevDomain+"add_subscription_plan";
    public static String Subscribe_plan                         = DevDomain+"display_subscription_data";

    public static String Search                                 = DevDomain+"search_result";

    public static String frgpass                                = DevDomain+"forgot_password";
    public static String limitTransaction                       = DevDomain+"check_entry_in_limit";
    public static String display_specific_profile               = DevDomain+"display_specific_profile";
    public static String change_dispute_status                  = DevDomain+"change_dispute_status";
    public static String declinefriend                          = DevDomain+"decline_friend_request";
    public static String addfriend                              = DevDomain+"send_friend_request";
    public static String acceptRequest                          = DevDomain+"accept_friend_request";
    public static String RequestList                            = DevDomain+"frnd_req_notification";
    public static String removefriend                           = DevDomain+"";
    public static String UploadProduct                          = DevDomain+"add_product_by_companies";
    public static String new_timelineAPI                        = DevDomain+"newtimeline_data_selflike";

    public static String AddtoCart                              = DevDomain+"add_to_cart";
    public static String CartProduct                            = DevDomain+"display_cart";;
    public static String UpdateCart                             = DevDomain+"edit_cart";
    public static String RemoveCart                             = DevDomain+"remove_from_cart";
    public static String uploadDPPage                           = DevDomain+"upload_logo";
    public static String profile_upload_dpic                    = DevDomain+"profile_upload_dpic";
    public static String company_product                        = DevDomain+"newtimeline_data_by_type";
    public static String display_address                        = DevDomain+"display_address";
    public static String Add_Address                            = DevDomain+"add_address";
    public static String order_placed                           = DevDomain+"order_placed";
    public static String updateChannel                          = DevDomain+"update_channel";
    public static String broadcast_fcm                          = DevDomain+"send_message";
    public static String fcm                                    = DevDomain+"send_single_message";
    public static String Chat_fcm                               = DevDomain+"chat_notification";
    public static String devicetoken                            = DevDomain+"token";
    public static String friend_suggestion                      = DevDomain+"friend_suggestion";
    public static String razorPaySuccess                        = DevDomain+"razorPaySuccess";
    public static String genrate_order                          = DevDomain+"order_id";
    public static String display_product_by_category            = DevDomain+"display_product_by_category";
    public static String order_history                          = DevDomain+"order_history";
}
